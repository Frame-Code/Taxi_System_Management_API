package com.taxi.external.client.openRouteService;

import dto.FullCoordinatesDTO;

public interface IOpenRouteServiceClient {
    String getResponse(FullCoordinatesDTO coordinates);
}
