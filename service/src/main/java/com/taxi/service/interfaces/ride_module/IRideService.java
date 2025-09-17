package com.taxi.service.interfaces.ride_module;

import dto.FullCoordinatesDTO;
import dto.DistanceInfoDTO;
import io.github.frame_code.domain.entities.Road;

import java.io.IOException;
import java.util.Optional;

public interface IRideService {
    Road save(Road road);
    double getTotalPrice(double distance, double duration);
    Optional<DistanceInfoDTO> getRideInfo(FullCoordinatesDTO coordinatesDTO) throws IOException;

}
