package com.taxi.service.interfaces.notification_module;

import dto.entities.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

public interface ISenderNotification<T extends Notification> {
    T send(NotificationDTO notificationDTO);
}
