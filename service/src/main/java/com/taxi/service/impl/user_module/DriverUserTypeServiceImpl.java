package com.taxi.service.impl.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import com.google.gson.Gson;
import com.taxi.service.interfaces.user_module.IUserTypeService;
import io.github.frame_code.domain.entities.Driver;
import io.github.frame_code.domain.entities.User;
import io.github.frame_code.domain.repository.IDriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverUserTypeServiceImpl implements IUserTypeService {
    private final IDriverRepository repository;

    @Override
    public ROLE_NAME getType() {
        return ROLE_NAME.DRIVER;
    }

    @Override
    public void create(User user, String additionalInformationJson) {
        Gson gson = new Gson();
        Driver deserialized = gson.fromJson(additionalInformationJson, Driver.class);
        Driver driver = Driver.builder()
                .user(user)
                .address(deserialized.getAddress())
                .license(deserialized.getLicense())
                .entryDate(deserialized.getEntryDate())
                .experienceYears(deserialized.getExperienceYears())
                .build();

        repository.save(driver);
    }
}
