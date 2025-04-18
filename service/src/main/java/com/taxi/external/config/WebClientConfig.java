package com.taxi.external.config;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient openCageAPI(WebClient.Builder webClientBuilder) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("https://api.opencagedata.com/geocode/v1/json");
        factory.setDefaultUriVariables(Map.of("key", "${API_KEY_OPEN_CAGE}"));
        return webClientBuilder
                .baseUrl("https://api.opencagedata.com/geocode/v1/json")
                .filter(((request, next) -> {
                    System.out.println("URI final: " + request.url());
                    return next.exchange(request);
                }))
                .build();
    }

}
