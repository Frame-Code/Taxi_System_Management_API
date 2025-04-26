package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiAddressRepository extends JpaRepository<TaxiLiveAddress, Long> {

}
