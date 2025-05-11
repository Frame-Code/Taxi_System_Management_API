package io.github.frame_code.domain.repository;

import Enums.entitiesEnums.STATUS_TAXI;
import io.github.frame_code.domain.config.TestJPAConfig;
import io.github.frame_code.domain.entities.Taxi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxiRepositoryTest {
    @Autowired
    TaxiRepository taxiRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

    @Test
    public void testCreate() {
        /*Taxi taxisaved = taxiRepository.save(Taxi.builder()
                .createdBy("Admin")
                .status(STATUS_TAXI.ENABLE)
                .driver(driverRepository.getReferenceById(10L))
                .vehicle(carRepository.getReferenceById(6L))
                .build());
        assertNotNull(taxisaved.getId());*/
    }
}