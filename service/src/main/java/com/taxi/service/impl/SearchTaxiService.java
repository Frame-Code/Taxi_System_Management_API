package com.taxi.service.impl;

import DTO.CoordinatesDTO;
import Utils.GeolocationUtils;
import com.taxi.service.interfaces.ISearchTaxiService;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import io.github.frame_code.domain.repository.TaxiLiveAddressRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchTaxiService implements ISearchTaxiService {
    private final TaxiLiveAddressRepository taxiLiveAddressRepository;

    @Override
    public List<Taxi> findNearbyCabs(CoordinatesDTO coordinatesDTO, double meters) {
        return taxiLiveAddressRepository.findNearbyTaxis(
                GeolocationUtils.coordinatesToWKT(coordinatesDTO.latitude(), coordinatesDTO.longitude()), meters)
                .stream()
                .map(TaxiLiveAddress::getTaxi)
                .toList();
    }
}
