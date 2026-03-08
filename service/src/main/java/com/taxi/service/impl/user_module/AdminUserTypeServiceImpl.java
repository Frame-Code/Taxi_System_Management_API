package com.taxi.service.impl.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import com.taxi.service.interfaces.user_module.IUserTypeService;
import io.github.frame_code.domain.entities.Admin;
import io.github.frame_code.domain.entities.User;
import io.github.frame_code.domain.repository.IAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserTypeServiceImpl implements IUserTypeService {
    private final IAdminRepository repository;

    @Override
    public ROLE_NAME getType() {
        return ROLE_NAME.ADMIN;
    }

    @Override
    public void create(User user, String additionalInformationJson) {
        Admin admin = Admin.builder()
                .user(user)
                .build();

        repository.save(admin);
    }
}
