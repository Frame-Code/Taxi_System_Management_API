package com.taxi.external.client.openCage;

import dto.entities.LocationDTO;

import java.util.Optional;

public interface IOpenCageClient {
    Optional<LocationDTO> reverse_geocoding(double latitude, double longitude);
}
