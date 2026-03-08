package com.taxi.service.interfaces.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import dto.entities.ClientDTO;
import dto.entities.NotificationDTO;
import dto.entities.TaxiDTO;
import dto.out.TaxiResponseDTO;

import java.util.Optional;

public interface IMatchMediator {
    NotificationDTO send(NotificationDTO notificationDTO);
    void updateStatus(REQUEST_STATUS status, Long idNotification);
    TaxiResponseDTO getResponse(Long id);
    Optional<TaxiDTO> match(TaxiDTO taxiDTO, ClientDTO clientDTO);
}
