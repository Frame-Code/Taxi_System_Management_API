package com.taxi.service.interfaces;

import DTO.CoordinatesDTO;
import DTO.LocationDTO;

import java.util.Optional;

public interface IParseCoordinatesService {
    Optional<LocationDTO> parseCoordinatesToLocation(CoordinatesDTO coordinatesDTO);
}
