package com.taxi.service.impl.notification_module;

import dto.TaxiResponseDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.notification_module.IRoadNotificationService;
import com.taxi.service.interfaces.notification_module.IRoadNotificationVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadNotificationVerifierImpl implements IRoadNotificationVerifier {
    private final IRoadNotificationService roadNotificationService;

    @Override
    public TaxiResponseDTO verifyResponse(Long notificationId) {
        var notification = roadNotificationService.findById(notificationId);
        return new TaxiResponseDTO(
                notification.map(n -> n.getStatus().equals(REQUEST_STATUS.ACCEPTED)).orElse(false),
                notification.map(n -> n.getStatus().equals(REQUEST_STATUS.REJECTED)).orElse(false),
                notification.map(n -> n.getStatus().equals(REQUEST_STATUS.TIMEOUT)).orElse(false));
    }
}
