package com.taxi.service.impl.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import com.taxi.service.interfaces.user_module.IUserTypeService;
import io.github.frame_code.domain.entities.Client;
import io.github.frame_code.domain.entities.User;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerUserTypeServiceImpl implements IUserTypeService {
    private final ClientRepository repository;

    @Override
    public ROLE_NAME getType() {
        return ROLE_NAME.CLIENT;
    }

    @Override
    public void create(User user, String additionalInformationJson) {
        Client client = Client.builder()
                .user(user)
                .build();

        repository.save(client);
    }
}
