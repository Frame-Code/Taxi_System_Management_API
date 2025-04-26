package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Taxi;
import junit.framework.TestCase;
import org.junit.Test;

public class TaxiRepositoryTest extends TestCase {
    TaxiRepository taxiRepository;

    public void testCreate() {
        taxiRepository.save(Taxi.builder()


                .build());
    }
}