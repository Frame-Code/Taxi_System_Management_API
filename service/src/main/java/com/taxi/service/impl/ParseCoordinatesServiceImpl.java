package com.taxi.service.impl;

import DTO.CoordinatesDTO;
import com.taxi.external.client.openCage.IOpenCageClient;
import com.taxi.external.service.IOpenCageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.taxi.service.interfaces.IParseCoordinatesService;

import DTO.LocationDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParseCoordinatesServiceImpl implements IParseCoordinatesService {
    private final IOpenCageClient openCageClient;
    private final IOpenCageService openCageService;

    @Override
    public Optional<LocationDTO> parseCoordinatesToLocation(CoordinatesDTO coordinatesDTO) {
        return openCageService.getLocationFromCoordinates(coordinatesDTO.latitude(), coordinatesDTO.longitude());
    }

}
