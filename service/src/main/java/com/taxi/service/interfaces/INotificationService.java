package com.taxi.service.interfaces;

import DTO.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

import java.util.Optional;

public interface INotificationService {
    Optional<Notification> send(NotificationDTO notificationDTO) throws NullPointerException;
    void setSender(SenderNotification senderNotification);
}
