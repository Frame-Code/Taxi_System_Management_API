package com.taxi.service.interfaces.notification_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import io.github.frame_code.domain.entities.RoadNotification;

import java.util.Optional;

public interface IRoadNotificationService extends INotificationService<RoadNotification>{
     boolean isRoadAccept(Long id);
     Optional<RoadNotification> findById(Long id);
     void updateStatus(REQUEST_STATUS status, Long id);
}
