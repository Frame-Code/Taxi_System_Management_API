package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import io.github.frame_code.domain.entities.RoadNotification;

public interface IRoadNotificationManager {
    RoadNotification sendNotification(NotificationDTO notificationDTO);
    void updateNotificationStatus(REQUEST_STATUS status, Long notificationId);
    void setRejected(Long notificationId);
    void setTimeOut(Long notificationId);
    void setAccepted(Long notificationId);
}
