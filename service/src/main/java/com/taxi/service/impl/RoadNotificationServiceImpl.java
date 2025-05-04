package com.taxi.service.impl;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.IRoadNotificationService;
import io.github.frame_code.domain.entities.RoadNotification;
import io.github.frame_code.domain.repository.ClientRepository;
import io.github.frame_code.domain.repository.DriverRepository;
import io.github.frame_code.domain.repository.RoadNotificationRepository;
import io.github.frame_code.domain.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@CommonsLog
@RequiredArgsConstructor
@Service
public class RoadNotificationServiceImpl implements IRoadNotificationService {
    private final RoadNotificationRepository roadNotificationRepository;
    private final TaxiRepository taxiRepository;
    private final ClientRepository clientRepository;

    @Override
    public RoadNotification send(String title, String message, Long idUser, Long idTaxi) {
        var client = clientRepository.findById(idUser);
        var taxi = taxiRepository.findById(idTaxi);

        return roadNotificationRepository.save(RoadNotification.builder()
                .client(client.get())
                .taxi(taxi.get())
                .title(title)
                .createdBy(client.get().getUser().getFullNames())
                .message(message)
                .status(REQUEST_STATUS.PENDING)
                .build());
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
