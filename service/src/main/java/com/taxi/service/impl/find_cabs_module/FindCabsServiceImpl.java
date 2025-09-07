package com.taxi.service.impl.find_cabs_module;

import dto.CoordinatesDTO;
import Enums.entitiesEnums.STATUS_TAXI;
import io.github.frame_code.domain.entities.Taxi;
import utils.GeolocationUtils;
import com.taxi.service.interfaces.find_cabs_module.IFindCabsService;
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
                .filter(taxi -> !taxi.isDeleted())
                .filter(taxi -> taxi.getStatus().equals(STATUS_TAXI.ENABLE))
                .toList();
    }
}
