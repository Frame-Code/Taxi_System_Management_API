package com.taxi.service.impl.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.notification_module.IRoadNotificationService;
import com.taxi.service.interfaces.notification_module.IRoadNotificationManager;
import io.github.frame_code.domain.entities.RoadNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoadNotificationManagerImpl implements IRoadNotificationManager {
    private final IRoadNotificationService roadNotificationService;

    @Override
    public RoadNotification sendNotification(NotificationDTO notificationDTO) {
        return roadNotificationService.send(notificationDTO);
    }

    @Override
    public void updateNotificationStatus(REQUEST_STATUS status, Long notificationId) {
        roadNotificationService.updateStatus(status, notificationId);
    }

    @Override
    public void setRejected(Long notificationId) {
        updateNotificationStatus(REQUEST_STATUS.REJECTED, notificationId);
    }

    @Override
    public void setTimeOut(Long notificationId) {
        updateNotificationStatus(REQUEST_STATUS.TIMEOUT, notificationId);
    }

    @Override
    public void setAccepted(Long notificationId) {
        updateNotificationStatus(REQUEST_STATUS.ACCEPTED, notificationId);
    }
}
