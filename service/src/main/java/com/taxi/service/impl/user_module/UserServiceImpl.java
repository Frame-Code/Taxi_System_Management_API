package com.taxi.service.impl.user_module;

import com.taxi.service.interfaces.user_module.IUserService;
import io.github.frame_code.domain.entities.User;
import io.github.frame_code.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
