package com.taxi.service.interfaces.ride_module;

import io.github.frame_code.domain.entities.Taxi;

public interface ICabService {
    Taxi findToGenerateRide(Long id);
}
