package com.taxi.service.interfaces.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import dto.AcceptRoadDTO;
import dto.ClientDTO;
import dto.ResponseSetStatusDTO;
import io.github.frame_code.domain.entities.Road;

public interface IRideUseCaseService {
    void acceptRoad(AcceptRoadDTO roadDTO, ClientDTO clientDTO);
    ResponseSetStatusDTO setStatus(STATUS_ROAD status, Long idRide);
}
