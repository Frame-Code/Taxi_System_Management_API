package com.taxi.service.impl.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import com.taxi.mappers.UserMapper;
import com.taxi.service.interfaces.user_module.IUserService;
import com.taxi.service.interfaces.user_module.IUserTypeService;
import dto.entities.RoleDto;
import dto.entities.UserDTO;
import dto.http.request.RegisterUserDto;
import io.github.frame_code.domain.entities.Permission;
import io.github.frame_code.domain.entities.Role;
import io.github.frame_code.domain.entities.User;
import io.github.frame_code.domain.repository.IRoleRepository;
import io.github.frame_code.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final IRoleRepository roleRepository;
    private final UserMapper mapper;
    private final Map<ROLE_NAME, IUserTypeService> userTypes;

    public UserServiceImpl(IUserRepository repository, IRoleRepository roleRepository, UserMapper mapper, List<IUserTypeService> strategies) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.userTypes = strategies.stream()
                .collect(Collectors.toMap(IUserTypeService::getType, s -> s));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean isNewUser(String email, String phone) {
        return repository.existsByEmailAndPhone(email, phone);
    }

    @Override
    public Optional<RoleDto> findByName(ROLE_NAME name) {
        return roleRepository.findByRoleName(name)
                .map(role -> new RoleDto(role.getId(), role.getRoleName(), role.getPermissions().stream().map(Permission::getPermissionName).toList()));
    }

    @Override
    public UserDTO save(RegisterUserDto userDto, final String passwordHash, final RoleDto role) {
        User user = mapper.toUser(userDto);
        user.setPasswordHash(passwordHash);
        user.setRole(Role.builder()
                .id(role.id())
                .build());

        User userSaved = repository.save(user);
        userTypes.get(userDto.rolName()).create(user, userDto.additionalInfoJson());
        return new UserDTO(userSaved.getNames(), userSaved.getLastNames(), userSaved.getBornDate(), userSaved.getEmail(), userSaved.getAge());
    }
}
