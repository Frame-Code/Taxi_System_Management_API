package com.taxi.service.interfaces;

import DTO.CoordinatesDTO;
import io.github.frame_code.domain.entities.Taxi;

import java.util.List;

public interface ISearchTaxiService {
    List<Taxi> findNearbyCabs(CoordinatesDTO coordinatesDTO, double meters);
}
