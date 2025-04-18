package com.taxi.service.interfaces;

import DTO.LocationDTO;

public interface IVerifyLocationService {
    boolean isLocationAvailable(LocationDTO locationDTO);
}
