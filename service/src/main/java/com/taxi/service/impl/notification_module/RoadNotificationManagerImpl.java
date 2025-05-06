package com.taxi.service.impl.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.notification_module.IRoadNotificationService;
import com.taxi.service.interfaces.notification_module.IRoadNotificationManager;
import io.github.frame_code.domain.entities.RoadNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoadNotificationManagerImpl implements IRoadNotificationManager {
    private final IRoadNotificationService roadNotificationService;

    @Override
    public Optional<RoadNotification> sendNotification(NotificationDTO notificationDTO) {
        return roadNotificationService.send(notificationDTO)
                .map(notification -> roadNotificationService.findById(notification.getId()))
                .orElse(Optional.empty());
    }

    @Override
    public void updateNotificationStatus(REQUEST_STATUS status, Long notificationId) {
        roadNotificationService.updateStatus(status, notificationId);
    }
}
