package com.taxi.service.interfaces.ws_ride;

import dto.ws.WsCommand;

public interface IRideEventService {
    void process(WsCommand command);
}
