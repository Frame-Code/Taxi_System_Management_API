package com.taxi.service.impl.ride_module;

import com.taxi.external.client.openRouteService.OpenRouteServiceClient;
import com.taxi.service.interfaces.ride_module.IRideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({RideServiceImpl.class, OpenRouteServiceClient.class})
@ContextConfiguration(classes = io.github.frame_code.domain.config.TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RideServiceImplTest {
    @Autowired
    IRideService rideService;

    @Test
    void getTotalPrice() {
        Double price = rideService.getTotalPrice(5, 2000);
        assertNotNull(price);
    }

    @Test
    void getRideInfo() {
    }
}