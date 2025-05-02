package com.taxi.service.impl;

import DTO.CoordinatesDTO;
import Enums.entitiesEnums.STATUS_TAXI;
import Utils.GeolocationUtils;
import com.taxi.service.interfaces.IFindCabsService;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import io.github.frame_code.domain.repository.TaxiLiveAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FindCabsServiceImpl implements IFindCabsService {
    private final TaxiLiveAddressRepository taxiLiveAddressRepository;

    @Override
    public List<Taxi> findNearbyCabs(CoordinatesDTO coordinatesDTO, double meters) {
        return taxiLiveAddressRepository.findNearbyTaxis(
                GeolocationUtils.coordinatesToWKT(coordinatesDTO.latitude(), coordinatesDTO.longitude()), meters)
                .stream()
                .map(TaxiLiveAddress::getTaxi)
                .filter(taxi -> taxi.getStatus().equals(STATUS_TAXI.ENABLE))
                .toList();
    }
}
