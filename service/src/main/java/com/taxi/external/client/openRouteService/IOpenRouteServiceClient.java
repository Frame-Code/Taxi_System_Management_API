package com.taxi.external.client.openRouteService;

import dto.FullCoordinatesDTO;
import reactor.core.publisher.Mono;

public interface IOpenRouteServiceClient {
    Mono<String> getResponse(FullCoordinatesDTO coordinates);
}
