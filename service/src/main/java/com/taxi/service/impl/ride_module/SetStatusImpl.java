package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import Enums.entitiesEnums.STATUS_ROAD;
import com.taxi.service.interfaces.ride_module.ISetStatus;
import dto.ResponseSetStatusDTO;
import io.github.frame_code.domain.entities.Road;
import io.github.frame_code.domain.repository.IRoadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@CommonsLog
@Service
@RequiredArgsConstructor
public class SetStatusImpl implements ISetStatus {
    private final IRoadRepository repository;

    @Override
    public ResponseSetStatusDTO set(STATUS_ROAD status, Long idRide) {
        Optional<Road> road = repository.findById(idRide);

        if(road.isEmpty()) return new ResponseSetStatusDTO("Ride not found", false);

        road.get().setStatus(status);
        repository.save(road.get());
        return new ResponseSetStatusDTO("Status saved", true);
    }
}
