package com.taxi.service.impl;

import DTO.CoordinatesToSearchDTO;
import DTO.LocationDTO;
import com.taxi.external.service.IOpenCageService;
import com.taxi.service.interfaces.ISearchTaxiService;
import io.github.frame_code.domain.entities.Taxi;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchTaxiService implements ISearchTaxiService {
    private final IOpenCageService openCageService;

    private List<Taxi> findTaxiByLocation() {
        return null;
    }

    @Override
    public List<Taxi> searchTaxis(CoordinatesToSearchDTO coordinatesToSearchDTO) {
        return List.of();
    }
}
