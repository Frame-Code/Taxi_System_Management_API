package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import io.github.frame_code.domain.entities.Notification;

public interface ISenderNotification {
    Notification send(NotificationDTO notificationDTO);
}
