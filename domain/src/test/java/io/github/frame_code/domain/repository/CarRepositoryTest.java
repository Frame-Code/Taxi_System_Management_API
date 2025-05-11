package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.config.TestJPAConfig;
import io.github.frame_code.domain.entities.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @Test
    public void saveTest() {
        /*Car carSaved = carRepository.save(Car.builder()
                .createdBy("admin")
                .brand("toyota")
                .color("blanco")
                .model("blanco a1")
                .year("2020")
                .chassisNumber("a1b2c3d4c5d6e7")
                .licensePlate("license5")
                .build());
        assertEquals("a1b2c3d4c5d6e7", carSaved.getChassisNumber());
        assertNotNull(carSaved.getId());*/
    }
}