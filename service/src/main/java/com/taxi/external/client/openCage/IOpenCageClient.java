package com.taxi.external.client.openCage;

import dto.LocationDTO;

import java.util.Optional;

public interface IOpenCageClient {
    Optional<LocationDTO> reverse_geocoding(double latitude, double longitude);
}
