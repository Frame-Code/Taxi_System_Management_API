package com.taxi.service.interfaces.location_module;

import dto.FunctionResponse;
import dto.LocationDTO;

import java.util.Optional;

public interface IVerifyLocationService {
    Optional<LocationDTO> isLocationAvailable(LocationDTO locationDTO);
}
