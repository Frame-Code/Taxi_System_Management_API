package com.taxi.service.interfaces.ride_module;

import io.github.frame_code.domain.entities.City;

public interface ICityService {
    City findToGenerateRide(Long id);
}
