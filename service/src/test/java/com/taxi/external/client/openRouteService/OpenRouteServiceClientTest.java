package com.taxi.external.client.openRouteService;

import dto.CoordinatesDTO;
import dto.FullCoordinatesDTO;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

@CommonsLog
class OpenRouteServiceClientTest {
    private static IOpenRouteServiceClient serviceClient;

    @BeforeAll
    public static void beforeAll() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .load();

            dotenv.entries().forEach(entry
                    -> System.setProperty(entry.getKey(), entry.getValue())
            );
            log.info("Environment variables loaded correctly");
        } catch (Exception e) {
            throw new RuntimeException("Error loading environment variables");
        }
        serviceClient = new OpenRouteServiceClient();
    }

    @Test
    void getResponse() {
        var mono = serviceClient.getResponse(new FullCoordinatesDTO(
                new CoordinatesDTO(-2.192284, -79.879845),
                new CoordinatesDTO(-2.196674, -79.883964)
        ));
        String response = mono.block(Duration.ofSeconds(10));
        assertNotNull(response);
        System.out.println(response);
    }
}