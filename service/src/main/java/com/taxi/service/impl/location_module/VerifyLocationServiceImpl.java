package com.taxi.service.impl.location_module;

import dto.LocationDTO;
import com.taxi.service.interfaces.location_module.IVerifyLocationService;
import io.github.frame_code.domain.entities.City;
import io.github.frame_code.domain.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerifyLocationServiceImpl implements IVerifyLocationService {
    private final ProvinceRepository provinceRepository;

    @Override
    public Optional<LocationDTO> isLocationAvailable(LocationDTO locationDTO) {
        Optional<City> city = provinceRepository
                .findCitiesByProvince(locationDTO.province())
                .stream()
                .filter(c -> c.getName().equals(locationDTO.city()))
                .findFirst();

        return city.map(value -> new LocationDTO(value.getId(), value.getName(), value.getProvince().getName(), locationDTO.road()));

    }
}
