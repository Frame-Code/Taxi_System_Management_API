package com.taxi.service.interfaces.ride_module;

import io.github.frame_code.domain.entities.Road;

public interface IRideService {
    double getTotalPrice(double distance, double duration);
    Road save(Road road);

}
