package com.taxi.service.interfaces.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import io.github.frame_code.domain.entities.RideStatus;

public interface IRideStatusService {
    RideStatus findByStatusNameToGenerateRide(STATUS_ROAD status);
}
