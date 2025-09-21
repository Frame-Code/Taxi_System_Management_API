package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.CityNotFoundException;
import com.taxi.service.interfaces.ride_module.ICityService;
import io.github.frame_code.domain.entities.City;
import io.github.frame_code.domain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
@RequiredArgsConstructor
public class CityServiceImpl implements ICityService {
    private final CityRepository repository;

    @Override
    public City findToGenerateRide(Long id) {
        return repository.findById(id)
                .orElseThrow(CityNotFoundException::new);
    }
}
