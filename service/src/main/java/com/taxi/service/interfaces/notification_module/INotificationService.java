package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

import java.util.Optional;

public interface INotificationService {
    Optional<Notification> send(NotificationDTO notificationDTO) throws NullPointerException;
    void setSender(ISenderNotification ISenderNotification);
}
