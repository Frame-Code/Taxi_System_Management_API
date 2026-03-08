package com.taxi.service.interfaces.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import dto.entities.RoleDto;
import dto.entities.UserDTO;
import dto.http.request.RegisterUserDto;
import io.github.frame_code.domain.entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);
    boolean isNewUser(String email, String phone);
    Optional<RoleDto> findByName(ROLE_NAME name);
    UserDTO save(RegisterUserDto userDto, final String passwordHash, final RoleDto role);

}
