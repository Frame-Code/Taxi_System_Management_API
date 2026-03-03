package com.taxi.service.interfaces.user_module;

import io.github.frame_code.domain.entities.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);

}
