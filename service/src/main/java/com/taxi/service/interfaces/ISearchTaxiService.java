package com.taxi.service.interfaces;

import DTO.CoordinatesToSearchDTO;
import io.github.frame_code.domain.entities.Taxi;

import java.util.List;

public interface ISearchTaxiService {
    List<Taxi> searchTaxis(CoordinatesToSearchDTO coordinatesToSearchDTO);
}
