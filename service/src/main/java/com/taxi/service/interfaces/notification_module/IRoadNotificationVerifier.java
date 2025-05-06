package com.taxi.service.interfaces.notification_module;

import dto.TaxiResponseDTO;

public interface IRoadNotificationVerifier {
    TaxiResponseDTO verifyResponse(Long notificationId);
}
