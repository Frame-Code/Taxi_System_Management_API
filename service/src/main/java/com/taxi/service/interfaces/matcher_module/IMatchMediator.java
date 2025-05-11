package com.taxi.service.interfaces.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import dto.ClientDTO;
import dto.NotificationDTO;
import dto.TaxiDTO;
import dto.TaxiResponseDTO;

import java.util.Optional;

public interface IMatchMediator {
    NotificationDTO send(NotificationDTO notificationDTO);
    void updateStatus(REQUEST_STATUS status, Long idNotification);
    TaxiResponseDTO getResponse(Long id);
    Optional<TaxiDTO> match(TaxiDTO taxiDTO, ClientDTO clientDTO);
}
