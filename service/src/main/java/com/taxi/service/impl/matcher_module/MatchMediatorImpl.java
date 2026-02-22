package com.taxi.service.impl.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.mappers.RoadNotificationMapper;
import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import com.taxi.service.interfaces.notification_module.IRoadNotificationManager;
import com.taxi.service.interfaces.notification_module.IRoadNotificationVerifier;
import dto.ClientDTO;
import dto.NotificationDTO;
import dto.TaxiDTO;
import dto.TaxiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class MatchMediatorImpl implements IMatchMediator {
    private final IRoadNotificationVerifier notificationVerifier;
    private final IRoadNotificationManager notificationManager;
    private final MatchService matchService;
    private final RoadNotificationMapper mapper;

    @Override
    public NotificationDTO send(NotificationDTO notificationDTO) {
        return mapper.toDTO(notificationManager.sendNotification(notificationDTO));
    }

    @Override
    public void updateStatus(REQUEST_STATUS status, Long idNotification) {
        notificationManager.updateNotificationStatus(status, idNotification);
    }

    @Override
    public TaxiResponseDTO getResponse(Long id) {
        return notificationVerifier.verifyResponse(id);
    }

    @Override
    public Optional<TaxiDTO> match(TaxiDTO taxiDTO, ClientDTO clientDTO) {
        return matchService.initMath(taxiDTO, clientDTO);
    }

}
