package com.taxi.external.service;

import DTO.LocationDTO;

import java.util.Optional;

public interface IOpenCageService {
    Optional<LocationDTO> getLocationFromCoordinates(double latitude, double longitude);
}
