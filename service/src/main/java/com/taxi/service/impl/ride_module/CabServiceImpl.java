package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.CabNotFoundException;
import com.taxi.service.interfaces.ride_module.ICabService;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.repository.ITaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
@RequiredArgsConstructor
public class CabServiceImpl implements ICabService {
    private final ITaxiRepository taxiRepository;

    @Override
    public Taxi findToGenerateRide(Long id) {
        return taxiRepository
                .findById(id)
                .orElseThrow(CabNotFoundException::new);
    }
}
