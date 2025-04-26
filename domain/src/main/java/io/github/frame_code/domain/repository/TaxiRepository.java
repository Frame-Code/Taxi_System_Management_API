package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
    @Query(value = "SELECT * FROM taxi t " +
            "WHERE ST_Distance_Sphere(t.taxi_live_addresses.location, ST_GeomFromText(:point, 4326)) " +
            "<= :meters", nativeQuery = true)
    List<Taxi> findNearbyTaxis(@Param("point") String pointWKT, @Param("meters") double meters );
}
