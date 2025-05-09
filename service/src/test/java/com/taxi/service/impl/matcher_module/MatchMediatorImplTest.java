package com.taxi.service.impl.matcher_module;

import dto.ClientDTO;
import dto.NotificationDTO;
import dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MatchMediatorImplTest {
    @Autowired
    MatchMediatorImpl matchMediator;


    @Test
    void send() {
        matchMediator.send(new NotificationDTO(
                "Request status",
                "fd",
                new ClientDTO(1L, new UserDTO()), null));
    }
}