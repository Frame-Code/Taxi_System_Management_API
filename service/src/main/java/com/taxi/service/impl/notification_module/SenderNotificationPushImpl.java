package com.taxi.service.impl.notification_module;

import dto.NotificationDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.notification_module.ISenderNotificationPush;
import io.github.frame_code.domain.entities.Notification;
import io.github.frame_code.domain.entities.RoadNotification;
import io.github.frame_code.domain.repository.ClientRepository;
import io.github.frame_code.domain.repository.RoadNotificationRepository;
import io.github.frame_code.domain.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service("push")
@CommonsLog
public class SenderNotificationPushImpl implements ISenderNotificationPush {
    private final RoadNotificationRepository roadNotificationRepository;
    private final TaxiRepository taxiRepository;
    private final ClientRepository clientRepository;


    @Override
    public Optional<Notification> send(NotificationDTO notificationDTO) {
        var client = clientRepository.findById(notificationDTO.clientDTO().id());
        var taxi = taxiRepository.findById(notificationDTO.taxiDTO().id());

        if(client.isEmpty() || taxi.isEmpty()) {
            log.warn("The info client or the info taxi empty, impossible send message ");
            return Optional.empty();
        }

        return Optional.of(roadNotificationRepository.save(RoadNotification.builder()
                .client(client.get())
                .taxi(taxi.get())
                .title(notificationDTO.title())
                .createdBy(client.get().getUser().getFullNames())
                .message(notificationDTO.message())
                .status(REQUEST_STATUS.PENDING)
                .build()));
    }
}
