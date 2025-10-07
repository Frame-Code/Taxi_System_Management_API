package com.taxi.service.interfaces.ride_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import Enums.entitiesEnums.STATUS_ROAD;
import dto.ResponseSetStatusDTO;

public interface ISetStatus {
    ResponseSetStatusDTO set(STATUS_ROAD status, Long idRide);
}
