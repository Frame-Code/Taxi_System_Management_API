package io.github.frame_code.domain.repository;

import Enums.entitiesEnums.STATUS_ROAD;
import io.github.frame_code.domain.entities.Road;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoadRepository extends JpaRepository<Road, Long> {

    /**
     * Busca la ruta activa del cliente identificado por email.
     * "Activa" = estado distinto de ENDED e INCONSISTENT.
     */
    @Query("SELECT r FROM Road r WHERE r.client.user.email = :email AND r.status.statusRoad IN :activeStatuses")
    Optional<Road> findActiveByClientEmail(
            @Param("email") String email,
            @Param("activeStatuses") List<STATUS_ROAD> activeStatuses
    );
}
