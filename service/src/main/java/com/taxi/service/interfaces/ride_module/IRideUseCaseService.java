package com.taxi.service.interfaces.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import dto.http.request.AcceptRoadDto;
import dto.entities.ClientDTO;
import dto.in.ResponseSetStatusDTO;
import io.github.frame_code.domain.entities.Road;

public interface IRideUseCaseService {
    Road findById(Long id);
    void acceptRoad(AcceptRoadDto roadDTO, ClientDTO clientDTO);
    ResponseSetStatusDTO setStatus(STATUS_ROAD status, Long idRide);
}
