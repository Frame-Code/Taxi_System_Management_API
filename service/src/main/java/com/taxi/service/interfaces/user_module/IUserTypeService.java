package com.taxi.service.interfaces.user_module;

import Enums.entitiesEnums.ROLE_NAME;
import io.github.frame_code.domain.entities.User;

public interface IUserTypeService {
    ROLE_NAME getType();
    void create(User user, String additionalInformationJson);
}
