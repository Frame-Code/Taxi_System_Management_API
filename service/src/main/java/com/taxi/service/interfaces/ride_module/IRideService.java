package com.taxi.service.interfaces.ride_module;

import dto.http.request.FullCoordinatesDTO;
import dto.out.DistanceInfoDTO;
import io.github.frame_code.domain.entities.Road;
import io.github.frame_code.domain.entities.RoadAddress;

import java.io.IOException;
import java.util.Optional;

public interface IRideService {
    Road save(Road road);
    double getTotalPrice(double distance, double duration);
    Optional<DistanceInfoDTO> getRideInfo(FullCoordinatesDTO coordinatesDTO) throws IOException;
    RoadAddress save(RoadAddress roadAddress);

    /** Devuelve true si el cliente (identificado por email) tiene una ruta no finalizada. */
    boolean hasActiveRide(String clientEmail);
}
