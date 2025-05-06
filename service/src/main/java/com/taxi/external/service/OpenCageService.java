package com.taxi.external.service;

import dto.LocationDTO;
import com.taxi.external.client.openCage.IOpenCageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenCageService implements IOpenCageService{

    private final IOpenCageClient openCageClient;

    @Override
    public Optional<LocationDTO> getLocationFromCoordinates(double latitude, double longitude) {
        return openCageClient.reverse_geocoding(latitude, longitude);
    }

}
