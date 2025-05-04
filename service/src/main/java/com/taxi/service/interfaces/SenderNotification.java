package com.taxi.service.interfaces;

import DTO.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

import java.util.Optional;

public interface SenderNotification {
    Optional<Notification> send(NotificationDTO notificationDTO);
}
