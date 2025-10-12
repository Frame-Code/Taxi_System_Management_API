package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import com.taxi.exceptions.RideStatusNotFoundException;
import com.taxi.service.interfaces.ride_module.IRideStatusService;
import io.github.frame_code.domain.entities.RideStatus;
import io.github.frame_code.domain.repository.IRideStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideStatusService implements IRideStatusService {
    private final IRideStatusRepository repository;

    @Override
    public RideStatus findByStatusNameToGenerateRide(STATUS_ROAD status) {
        return repository.findByStatus(status)
                .orElseThrow(RideStatusNotFoundException::new);
    }
}
