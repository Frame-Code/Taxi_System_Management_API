package com.taxi.service.impl.ws_ride;

import com.taxi.service.interfaces.ride_module.IRideStatusService;
import com.taxi.service.interfaces.ride_module.IRideUseCaseService;
import com.taxi.service.interfaces.ws_ride.IRideEventService;
import dto.ws.WsCommand;
import dto.ws.WsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideEventService implements IRideEventService {
    private final IRideStatusService statusService;
    private final IRideUseCaseService useCaseService;
    private final SimpMessagingTemplate messaging;

    @Override
    public void process(WsCommand command) {
        statusService.findByStatus(command.status());
        useCaseService.findById(command.idRide());

        String destination = "/room/ride." + command.idRide();
        WsResponse response = new WsResponse(LocalDateTime.now(), "Status change successfully");
        messaging.convertAndSend(destination, response);
    }
}
