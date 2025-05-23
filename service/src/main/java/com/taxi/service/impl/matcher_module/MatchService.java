package com.taxi.service.impl.matcher_module;

import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import com.taxi.service.interfaces.scheduler_module.IMatchingScheduler;
import dto.ClientDTO;
import dto.NotificationDTO;
import dto.TaxiDTO;
import dto.TaxiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@CommonsLog
@Service
@RequiredArgsConstructor
public class MatchService {
    @Autowired
    @Lazy
    private IMatchMediator mediator;

    private final IMatchingScheduler scheduler;

    public Optional<TaxiDTO> initMath(TaxiDTO taxiDTO, ClientDTO clientDTO) {
        AtomicReference<Optional<TaxiDTO>> taxiDTOAtomicReference = new AtomicReference<>(Optional.empty());
        AtomicReference<NotificationDTO> notificationDTOAtomicReference = new AtomicReference<>();
        sendNotification(taxiDTO, clientDTO, notificationDTOAtomicReference);

        scheduler.scheduleUnique(() -> {
            setTimeOut(notificationDTOAtomicReference);
            closeScheduledFuture((ScheduledFuture<?>) this);
        }, 13);

        scheduler.schedulePeriod(() -> {
            log.info("Waiting response of taxi with id: " + taxiDTO.id());
            TaxiResponseDTO taxiResponse = mediator.getResponse(notificationDTOAtomicReference.get().getId());

            if (taxiResponse.isAccepted()) {
                processResponse(taxiDTOAtomicReference, Optional.of(taxiDTO), REQUEST_STATUS.ACCEPTED);
                closeScheduledFuture((ScheduledFuture<?>) this);
                return;
            }
            if(taxiResponse.isRejected()) {
                processResponse(taxiDTOAtomicReference, Optional.empty(), REQUEST_STATUS.REJECTED);
                closeScheduledFuture((ScheduledFuture<?>) this);
            }
        }, 0, 2);
        var executorPeriod = scheduler.getExecutor();

        try {
            executorPeriod.awaitTermination(13, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("Interrupted task finishing");
            Thread.currentThread().interrupt();
        }

       if (!executorPeriod.isShutdown()) {
            executorPeriod.shutdownNow();
            log.info("ScheduledFuturePeriodReview finished");
        }
        return taxiDTOAtomicReference.get();
    }

    private void processResponse(@NotNull AtomicReference<Optional<TaxiDTO>> atomicReference, Optional<TaxiDTO> response, @NotNull REQUEST_STATUS status) {
        log.info("Status road: " + status.name().toLowerCase());
        atomicReference.set(response);
    }

    private void closeScheduledFuture(@NotNull ScheduledFuture<?> schedule) {
        schedule.cancel(false);
    }

    private void sendNotification(TaxiDTO taxiDTO, ClientDTO clientDTO, @NotNull AtomicReference<NotificationDTO> notificationDTOAtomicReference) {
        notificationDTOAtomicReference.set(mediator.send(NotificationDTO.builder()
                .title("New road message!!")
                .message("A new client want a road!!")
                .taxiDTO(taxiDTO)
                .clientDTO(clientDTO)
                .build()));

    }

    private void setTimeOut(AtomicReference<NotificationDTO> notificationDTOAtomicReference) {
        log.info("Wait response timeout...");
        mediator.updateStatus(REQUEST_STATUS.TIMEOUT, notificationDTOAtomicReference.get().getId());
    }
}
