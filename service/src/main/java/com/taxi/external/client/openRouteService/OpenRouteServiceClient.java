package com.taxi.external.client.openRouteService;

import dto.CoordinatesToSearchDTO;
import dto.FullCoordinatesDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@CommonsLog
public class OpenRouteServiceClient implements IOpenRouteServiceClient{
    private final String API_KEY;
    private final WebClient webClient;

    public OpenRouteServiceClient(WebClient webClient) {
        this.API_KEY = System.getProperty("API_KEY_OPEN_ROUTE_SERVICE");
        this.webClient = WebClient.create("https://api.openrouteservice.org");
    }

    @Override
    public String getResponse(FullCoordinatesDTO coordinates) {
        Map<String, String> params = Map.of(
                "api_key", API_KEY,
                "start", coordinates.getOrigin(","),
                "end", coordinates.getDestiny(",")
        );
        Map<String, String> headers = Map.of(
                "Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8"
        );

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/directions/driving-car?")
                        .queryParam("api_key", API_KEY)
                        .queryParam("start", coordinates.getOrigin(","))
                        .queryParam("end", coordinates.getDestiny(","))
                        .build())
                .retrieve()
                .bodyToMono(String);
    }
}
