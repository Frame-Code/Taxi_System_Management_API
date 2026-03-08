package com.taxi.external.client.openRouteService;

import dto.http.request.FullCoordinatesDTO;
import reactor.core.publisher.Mono;

public interface IOpenRouteServiceClient {
    Mono<String> getResponse(FullCoordinatesDTO coordinates);
}
