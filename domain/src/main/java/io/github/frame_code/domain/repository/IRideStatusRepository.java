package io.github.frame_code.domain.repository;

import Enums.entitiesEnums.STATUS_ROAD;
import io.github.frame_code.domain.entities.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRideStatusRepository extends JpaRepository<RideStatus, Long> {

    @Query("SELECT r FROM RideStatus r WHERE r.statusRoad = :status")
    Optional<RideStatus> findByStatus(@Param("status")STATUS_ROAD status);

}
