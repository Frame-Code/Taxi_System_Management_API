package com.taxi.service.impl.ride_module;

import com.taxi.external.client.openRouteService.OpenRouteServiceClient;
import com.taxi.service.interfaces.ride_module.IRideService;
import dto.CoordinatesDTO;
import dto.FullCoordinatesDTO;
import dto.RideInfoDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({RideServiceImpl.class, OpenRouteServiceClient.class})
@ContextConfiguration(classes = io.github.frame_code.domain.config.TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@CommonsLog
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
        try {
            Optional<RideInfoDTO> rideInfoDTO = rideService.getRideInfo(
                    new FullCoordinatesDTO(
                            new CoordinatesDTO(-2.192284, -79.879845),
                            new CoordinatesDTO(-2.155990, -79.891978)
                    )
            );
            assertNotNull(rideInfoDTO);
        }catch (IOException ex) {
            log.error("Error, can't get the ride info, please verify the API");
        }
    }

    @Test
    void integration() {
        try {
            Optional<RideInfoDTO> rideInfoDTO = rideService.getRideInfo(
                    new FullCoordinatesDTO(
                            new CoordinatesDTO(-2.192284, -79.879845),
                            new CoordinatesDTO(-2.155990, -79.891978)
                    )
            );
            assertNotNull(rideInfoDTO);
            assertTrue(rideInfoDTO.isPresent());
            Double price = rideService.getTotalPrice(rideInfoDTO.get().approxDistance(), rideInfoDTO.get().approxTime());
            assertNotNull(price);

        } catch (IOException ex) {
            log.error("Error, can't get the ride info, please verify the API");
        }
    }
}