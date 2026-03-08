package com.taxi.service.interfaces.location_module;

import dto.in.CoordinatesDTO;
import dto.entities.LocationDTO;

import java.util.Optional;

public interface IParseCoordinatesService {
    Optional<LocationDTO> parseCoordinatesToLocation(CoordinatesDTO coordinatesDTO);
}
