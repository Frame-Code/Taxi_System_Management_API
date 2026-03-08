package com.taxi.service.interfaces.location_module;

import dto.entities.LocationDTO;

import java.util.Optional;

public interface IVerifyLocationService {
    Optional<LocationDTO> isLocationAvailable(LocationDTO locationDTO);
}
