package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.config.TestJPAConfig;
import io.github.frame_code.domain.entities.Driver;
import io.github.frame_code.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DriverRepositoryTest{
    @Autowired
    DriverRepository driverRepository;

    @Test
    public void testSave() {
        Driver savedDriver = driverRepository.save(Driver.builder()
                .user(User.builder()
                        .bornDate(LocalDate.now())
                        .email("mail5@email.com")
                        .lastNames("Reyna Smith")
                        .names("Jon Saul")
                        .passwordHash("jkghlkljkljkljdsdkljkljkqwl")
                        .phone("0941256")
                        .createdBy("admin")
                        .build())
                .address("any place")
                .entryDate(LocalDate.now())
                .build());
        assertThat(savedDriver.getId()).isNotNull();
        assertThat(savedDriver.getUser().getEmail()).isEqualTo("mail5@email.com");

    }

}