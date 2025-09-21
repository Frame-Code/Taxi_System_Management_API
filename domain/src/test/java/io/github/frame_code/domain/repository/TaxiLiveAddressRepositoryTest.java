package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.config.TestJPAConfig;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ContextConfiguration(classes = TestJPAConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Log
public class TaxiLiveAddressRepositoryTest {
    @Autowired
    TaxiLiveAddressRepository taxiLiveAddressRepository;

    @Autowired
    ITaxiRepository taxiRepository;

    @Test
    public void testSave() {
        /*TaxiLiveAddress taxiLiveAddressSaved = taxiLiveAddressRepository.save(TaxiLiveAddress.builder()
                .location(GeolocationUtils.createPoint(-665564, 65765))
                .reference("any place")
                .taxi(taxiRepository.getReferenceById(9L))
                .build());
        assertNotNull(taxiLiveAddressSaved.getId());
    }

    @Test
    public void nearbyTaxisTest() {
        List<TaxiLiveAddress> taxiLiveAddressSaved = taxiLiveAddressRepository.findNearbyTaxis(
                GeolocationUtils.coordinatesToWKT(-6435457,-5351354),
                8000.0 );
        assertNotNull(taxiLiveAddressSaved);*/
    }
}