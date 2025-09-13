package com.taxi.external.client.openRouteService;

import dto.FullCoordinatesDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@CommonsLog
public class OpenRouteServiceClient implements IOpenRouteServiceClient{
    private final String API_KEY;
    private final WebClient webClient;

    public OpenRouteServiceClient() {
        this.API_KEY = System.getProperty("API_KEY_OPEN_ROUTE_SERVICE");
        this.webClient = WebClient.create("https://api.openrouteservice.org");

    }

    @Override
    public Mono<String> getResponse(FullCoordinatesDTO coordinates) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/directions/driving-car")
                        .queryParam("api_key", API_KEY)
                        .queryParam("start", coordinates.getOrigin(","))
                        .queryParam("end", coordinates.getDestiny(","))
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
