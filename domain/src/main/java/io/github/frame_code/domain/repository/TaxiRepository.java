package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    List<Taxi> findTaxisByLocation();
}
