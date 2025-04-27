package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.TaxiLiveAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxiLiveAddressRepository extends JpaRepository<TaxiLiveAddress, Long> {
    @Query(value = """
            SELECT *
            FROM address
            INNER JOIN taxi_live_address
            ON address.id = taxi_live_address.id
            WHERE ST_Distance_Sphere(address.location, ST_GeomFromText(:point, 4326)) <= :meters""", nativeQuery = true)
    List<TaxiLiveAddress> findNearbyTaxis(@Param("point") String pointWKT, @Param("meters") double meters );
}
