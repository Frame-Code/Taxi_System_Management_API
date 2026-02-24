package com.taxi.service.interfaces.ride_module;

import Enums.entitiesEnums.STATUS_TAXI;
import io.github.frame_code.domain.entities.Taxi;

public interface ICabService {
    Taxi findToGenerateRide(Long id);
    void setStatus(Long id, STATUS_TAXI statusTaxi);
}
