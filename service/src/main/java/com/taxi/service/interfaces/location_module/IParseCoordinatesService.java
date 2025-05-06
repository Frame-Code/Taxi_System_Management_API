package com.taxi.service.interfaces.location_module;

import dto.CoordinatesDTO;
import dto.LocationDTO;

import java.util.Optional;

public interface IParseCoordinatesService {
    Optional<LocationDTO> parseCoordinatesToLocation(CoordinatesDTO coordinatesDTO);
}
