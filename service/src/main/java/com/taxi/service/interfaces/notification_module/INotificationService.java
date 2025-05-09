package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

public interface INotificationService {
    Notification send(NotificationDTO notificationDTO);
    void setSender(ISenderNotification ISenderNotification);
}
