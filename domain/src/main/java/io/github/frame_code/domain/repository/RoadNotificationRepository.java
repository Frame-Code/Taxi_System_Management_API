package io.github.frame_code.domain.repository;

import io.github.frame_code.domain.entities.RoadNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadNotificationRepository extends JpaRepository<RoadNotification, Long> {
}
