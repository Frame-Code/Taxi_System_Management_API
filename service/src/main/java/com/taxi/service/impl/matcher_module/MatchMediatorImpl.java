package com.taxi.service.impl.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.mappers.RoadNotificationMapper;
import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import com.taxi.service.interfaces.notification_module.IRoadNotificationManager;
import com.taxi.service.interfaces.notification_module.IRoadNotificationVerifier;
import com.taxi.service.interfaces.scheduler_module.IMatchingScheduler;
import com.taxi.service.interfaces.scheduler_module.ITaxiMatchingScheduler;
import dto.ClientDTO;
import dto.NotificationDTO;
import dto.TaxiDTO;
import dto.TaxiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
@CommonsLog
@RequiredArgsConstructor
public class MatchMediatorImpl implements IMatchMediator {
    private final IMatchingScheduler scheduler;
    private final IRoadNotificationVerifier notificationVerifier;
    private final IRoadNotificationManager notificationManager;

    @Override
    public NotificationDTO send(NotificationDTO notificationDTO) {
        return RoadNotificationMapper.INSTANCE.toDTO(notificationManager.sendNotification(notificationDTO));
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
        final AtomicReference<Optional<TaxiDTO>> taxiDTOAtomicReference = new AtomicReference<>();
        final AtomicReference<NotificationDTO> notificationDTOAtomicReference = new AtomicReference<>();
        notificationDTOAtomicReference.set(send(NotificationDTO.builder()
                .title("New road message!!")
                .message("A new client want a road!!")
                .taxiDTO(taxiDTO)
                .clientDTO(clientDTO)
                .build()));

        scheduler.schedulePeriod(() -> {
            var taxiResponse = getResponse(notificationDTOAtomicReference.get().getId());

            if (taxiResponse.isAccepted()) {
                log.info("Road accepted");
                processResponse(taxiDTOAtomicReference, Optional.of(taxiDTO));
            }
            if(taxiResponse.isRejected()) {
                log.info("Road rejected");
                processResponse(taxiDTOAtomicReference, Optional.empty());
            }
            if(taxiResponse.isTimeOut()) {
                log.info("Road timeout");
                processResponse(taxiDTOAtomicReference, Optional.empty());
            }

            log.info("Waiting response of taxi with id: " + taxiDTO.id());
        }, 0, 2);

        scheduler.scheduleUnique(() -> {
            log.info("Notification with the id " + notificationDTOAtomicReference.get().getId() + "timeout");
            notificationManager.setTimeOut(notificationDTOAtomicReference.get().getId());
            scheduler.shutdownNow();
        }, 10);

        try {
            scheduler.getExecutor().awaitTermination((long) 3 * 10 + 10, TimeUnit.SECONDS);
            log.info("All task ended");
        } catch (InterruptedException e) {
            log.warn("Interrupted task finishing");
            Thread.currentThread().interrupt();
        }

        if (!scheduler.getExecutor().isShutdown()) {
            scheduler.getExecutor().shutdownNow();
            log.info("ScheduleExecutorService finished");
        }
        return taxiDTOAtomicReference.get();
    }

    private void processResponse(AtomicReference<Optional<TaxiDTO>> atomicReference, Optional<TaxiDTO> response) {
        atomicReference.set(response);
        scheduler.shutdownNow();
        log.info("Shutting down the scheduler");
    }
}
