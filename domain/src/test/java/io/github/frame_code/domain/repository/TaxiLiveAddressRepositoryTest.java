package io.github.frame_code.domain.repository;

import Utils.GeolocationUtils;
import io.github.frame_code.domain.config.TestJPAConfig;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaxiLiveAddressRepositoryTest {
    @Autowired
    TaxiLiveAddressRepository taxiLiveAddressRepository;

    @Autowired
    TaxiRepository taxiRepository;

    @Test
    public void testSave() {
        TaxiLiveAddress taxiLiveAddressSaved = taxiLiveAddressRepository.save(TaxiLiveAddress.builder()
                .location(GeolocationUtils.createPoint(-2.174030, -79.891824))
                .reference("parroquia tarqui")
                .taxi(taxiRepository.getReferenceById(9L))
                .build());
        assertNotNull(taxiLiveAddressSaved.getId());
    }
}