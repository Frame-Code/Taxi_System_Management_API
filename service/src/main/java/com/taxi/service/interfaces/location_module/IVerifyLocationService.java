package com.taxi.service.interfaces.location_module;

import dto.LocationDTO;

public interface IVerifyLocationService {
    boolean isLocationAvailable(LocationDTO locationDTO);
}
