package com.taxi.external.config;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient openCageAPI(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("https://api.opencagedata.com/geocode/v1/json?")
                .defaultUriVariables(Map.of("key", "${API_KEY_OPEN_CAGE}"))
                .build();
    }

}
