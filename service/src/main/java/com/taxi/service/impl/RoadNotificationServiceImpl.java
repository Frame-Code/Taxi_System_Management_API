package com.taxi.service.impl;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.IRoadNotificationService;

import DTO.NotificationDTO;
import com.taxi.service.interfaces.SenderNotification;
import io.github.frame_code.domain.entities.Notification;
import io.github.frame_code.domain.entities.RoadNotification;
import io.github.frame_code.domain.repository.RoadNotificationRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@CommonsLog
@Service
public class RoadNotificationServiceImpl implements IRoadNotificationService {
    @Autowired
    @Qualifier(value = "push")
    private SenderNotification senderNotification;

    @Autowired
    private RoadNotificationRepository roadNotificationRepository;

    @Override
    public Optional<Notification> send(NotificationDTO notificationDTO) throws NullPointerException{
        return senderNotification.send(notificationDTO);
    }

    @Override
    public void setSender(SenderNotification senderNotification) {
        this.senderNotification = senderNotification;
    }

    @Override
    public boolean isRoadAccept(Long id) {
        return roadNotificationRepository.findById(id)
                .map(roadNotification -> roadNotification.getStatus().equals(REQUEST_STATUS.ACCEPTED))
                .orElse(false);
    }

    @Override
    public Optional<RoadNotification> findById(Long id) {
        return roadNotificationRepository.findById(id);
    }

    @Override
    public void updateStatus(REQUEST_STATUS status, Long id) {
        var notification = findById(id);
        if(notification.isEmpty()) {
            log.warn("Notification to update status not founded");
            return;
        }

        notification.get().setStatus(status);
        roadNotificationRepository.save(notification.get());
    }

}
