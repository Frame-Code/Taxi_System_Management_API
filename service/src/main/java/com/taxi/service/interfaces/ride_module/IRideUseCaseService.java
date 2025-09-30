package com.taxi.service.interfaces.ride_module;

import dto.AcceptRoadDTO;
import dto.ClientDTO;
import io.github.frame_code.domain.entities.Road;

public interface IRideUseCaseService {
    void acceptRoad(AcceptRoadDTO roadDTO, ClientDTO clientDTO);
}
