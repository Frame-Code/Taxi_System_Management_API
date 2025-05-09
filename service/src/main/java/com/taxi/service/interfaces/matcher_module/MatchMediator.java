package com.taxi.service.interfaces.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import dto.NotificationDTO;
import dto.TaxiResponseDTO;

public interface MatchMediator {
    NotificationDTO send(NotificationDTO notificationDTO);
    void updateStatus(REQUEST_STATUS status, Long idNotification);
    TaxiResponseDTO getResponse(Long id);
}
