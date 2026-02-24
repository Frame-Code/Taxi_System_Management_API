package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_TAXI;
import com.taxi.exceptions.CabNotFoundException;
import com.taxi.exceptions.InvalidStatusCabException;
import com.taxi.service.interfaces.ride_module.ICabService;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.repository.ITaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class CabServiceImpl implements ICabService {
    private final ITaxiRepository taxiRepository;

    @Override
    public Taxi findToGenerateRide(Long id) {
        return taxiRepository
                .findById(id)
                .filter(cab -> {
                    if(!cab.isEnable()) throw new CabNotFoundException("The cab selected is not enable to work");
                    return true;
                })
                .orElseThrow(CabNotFoundException::new);
    }

    @Override
    public void setStatus(Long id, STATUS_TAXI statusTaxi) {
        Optional<Taxi> cab = taxiRepository.findById(id);
        if(cab.isEmpty() || (cab.get().getStatus() == STATUS_TAXI.DISABLE || cab.get().getStatus() == STATUS_TAXI.WORKING))
            throw new CabNotFoundException("Fatal error: the cab selected does not exist or is not available");

        if(!isPossibleSetStatus(cab.get().getStatus(), statusTaxi))
            throw new InvalidStatusCabException();

        cab.get().setStatus(statusTaxi);
        taxiRepository.save(cab.get());
    }

    private boolean isPossibleSetStatus(STATUS_TAXI currentStatus, STATUS_TAXI statusToSet) {
        return switch (currentStatus) {
            case WORKING, ENABLE -> true;
            case DISABLE -> statusToSet != STATUS_TAXI.WORKING;
        };
    }
}
