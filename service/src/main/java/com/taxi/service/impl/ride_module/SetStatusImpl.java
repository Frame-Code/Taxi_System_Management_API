package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import com.taxi.service.interfaces.ride_module.ISetStatus;
import dto.ResponseSetStatusDTO;
import io.github.frame_code.domain.entities.RideStatus;
import io.github.frame_code.domain.entities.Road;
import io.github.frame_code.domain.repository.IRideStatusRepository;
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
    private final IRideStatusRepository statusRepository;

    @Override
    public ResponseSetStatusDTO set(STATUS_ROAD status, Long idRide) {
        Optional<Road> road = repository.findById(idRide);
        Optional<RideStatus> rideStatus = statusRepository.findByStatus(status);

        if(road.isEmpty() || rideStatus.isEmpty())
            return new ResponseSetStatusDTO("Ride not found", false);

        if(!isPosibleSet(road.get().getStatus(), rideStatus.get()))
            return new ResponseSetStatusDTO("Invalid status to set, the order is not correct", false);
        road.get().setStatus(rideStatus.get());
        repository.save(road.get());
        return new ResponseSetStatusDTO("Status saved", true);
    }

    private boolean isPosibleSet(RideStatus currentStatus, RideStatus statusToSet) {
        return currentStatus.getStatusOrder() < statusToSet.getStatusOrder();
    }
}
