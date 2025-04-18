package com.taxi.external.client.openCage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenCageClient implements IOpenCageClient {
    private final WebClient openCageApi;

    @Override
    public String reverse_geocoding(double latitude, double longitude) {
        return openCageApi
                .get()
                .attribute("q", String.valueOf(latitude) + "+" + String.valueOf(longitude))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
