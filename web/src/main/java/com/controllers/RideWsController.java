package com.controllers;

import com.taxi.service.interfaces.ws_ride.IRideEventService;
import dto.ws.WsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RideWsController {
    private final IRideEventService service;

    @MessageMapping("ride/status-ws")
    public void receiveStatus(WsCommand command) {
        service.process(command);
    }

}
