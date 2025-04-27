package io.github.frame_code.domain.repository;

import Utils.GeolocationUtils;
import io.github.frame_code.domain.config.TestJPAConfig;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log
public class TaxiLiveAddressRepositoryTest {
    @Autowired
    TaxiLiveAddressRepository taxiLiveAddressRepository;

    @Autowired
    TaxiRepository taxiRepository;

    @Test
    @Rollback(value = false)
    public void testSave() {
        TaxiLiveAddress taxiLiveAddressSaved = taxiLiveAddressRepository.save(TaxiLiveAddress.builder()
                .location(GeolocationUtils.createPoint(-2.153026, -79.826456))
                .reference("america del sur")
                .taxi(taxiRepository.getReferenceById(2L))
                .build());
        assertNotNull(taxiLiveAddressSaved.getId());
    }

    @Test
    public void nearbyTaxisTest() {
        List<TaxiLiveAddress> taxiLiveAddressSaved = taxiLiveAddressRepository.findNearbyTaxis(
                GeolocationUtils.coordinatesToWKT(-77.45, -10.66),
                5000.0 );
        assertNotNull(taxiLiveAddressSaved);
    }
}