package com.taxi.service.impl.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.mappers.RoadNotificationMapper;
import com.taxi.service.interfaces.matcher_module.MatchMediator;
import com.taxi.service.interfaces.notification_module.IRoadNotificationManager;
import com.taxi.service.interfaces.notification_module.IRoadNotificationVerifier;
import com.taxi.service.interfaces.scheduler_module.IMatchingScheduler;
import dto.NotificationDTO;
import dto.TaxiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchMediatorImpl implements MatchMediator {
    private final IMatchingScheduler matchingScheduler;
    private final IRoadNotificationVerifier notificationVerifier;
    private final IRoadNotificationManager notificationManager;

    @Override
    public NotificationDTO send(NotificationDTO notificationDTO) {
        return RoadNotificationMapper.INSTANCE.toDTO(notificationManager.sendNotification(notificationDTO));
    }

    @Override
    public void updateStatus(REQUEST_STATUS status, Long idNotification) {

    }

    @Override
    public TaxiResponseDTO getResponse(Long id) {
        return null;
    }
}
