package com.taxi.service.interfaces.ride_module;

import io.github.frame_code.domain.entities.Client;

public interface IClientService {
    Client findToGenerateRide(Long id);
}
