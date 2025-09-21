package com.taxi.service.impl.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.notification_module.ISenderRoadNotificationPush;
import io.github.frame_code.domain.entities.RoadNotification;
import io.github.frame_code.domain.repository.ClientRepository;
import io.github.frame_code.domain.repository.RoadNotificationRepository;
import io.github.frame_code.domain.repository.ITaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("push")
@CommonsLog
public class SenderRoadNotificationPushImpl implements ISenderRoadNotificationPush {
    private final RoadNotificationRepository roadNotificationRepository;
    private final ITaxiRepository taxiRepository;
    private final ClientRepository clientRepository;

    @Override
    public RoadNotification send(NotificationDTO notificationDTO) {
        var client = clientRepository.findById(notificationDTO.getClientDTO().id());
        var taxi = taxiRepository.findById(notificationDTO.getTaxiDTO().id());

        return roadNotificationRepository.save(RoadNotification.builder()
                .client(client.orElseThrow())
                .taxi(taxi.orElseThrow())
                .title(notificationDTO.getTitle())
                .createdBy(client.get().getUser().getFullNames())
                .message(notificationDTO.getMessage())
                .status(REQUEST_STATUS.PENDING)
                .build());
    }
}
