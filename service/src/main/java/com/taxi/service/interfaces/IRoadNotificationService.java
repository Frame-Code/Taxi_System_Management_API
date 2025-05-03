package com.taxi.service.interfaces;

import io.github.frame_code.domain.entities.RoadNotification;

import java.util.Optional;

public interface IRoadNotificationService {
     RoadNotification send(String title, String message, Long idUser, Long idTaxi);
     boolean isRoadAccept(Long id);
     Optional<RoadNotification> findById(Long id);
}
