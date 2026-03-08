package com.taxi.service.interfaces.notification_module;

import dto.out.TaxiResponseDTO;

public interface IRoadNotificationVerifier {
    TaxiResponseDTO verifyResponse(Long notificationId);
}
