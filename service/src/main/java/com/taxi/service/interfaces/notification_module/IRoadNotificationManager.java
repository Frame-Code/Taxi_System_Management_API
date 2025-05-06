package com.taxi.service.interfaces.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import io.github.frame_code.domain.entities.RoadNotification;

import java.util.Optional;

public interface IRoadNotificationManager {
    Optional<RoadNotification> sendNotification(NotificationDTO notificationDTO);
    void updateNotificationStatus(REQUEST_STATUS status, Long notificationId);

}
