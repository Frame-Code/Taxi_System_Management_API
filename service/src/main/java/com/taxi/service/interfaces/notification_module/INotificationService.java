package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

public interface INotificationService<T extends Notification> {
    T send(NotificationDTO notificationDTO);
    void setSender(ISenderNotification<T> ISenderNotification);
}
