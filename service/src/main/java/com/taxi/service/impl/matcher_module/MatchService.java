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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
        AtomicInteger initialDelay = new AtomicInteger(0);
        AtomicReference<Optional<TaxiDTO>> taxiDTOAtomicReference = new AtomicReference<>(Optional.empty());
        AtomicReference<NotificationDTO> notificationDTOAtomicReference = new AtomicReference<>();

        sendNotification(taxiDTO, clientDTO, notificationDTOAtomicReference);

        ScheduledFuture<?> periodReview = scheduler.schedulePeriod(() -> {
            log.info("Waiting response of taxi with id: " + taxiDTO.id());
            TaxiResponseDTO taxiResponse = mediator.getResponse(notificationDTOAtomicReference.get().getId());

            if (taxiResponse.isAccepted()) {
                processResponse(taxiDTOAtomicReference, Optional.of(taxiDTO), REQUEST_STATUS.ACCEPTED, (ScheduledFuture<?>) this);
                return;
            }
            if(taxiResponse.isTimeOut()) {
                processResponse(taxiDTOAtomicReference, Optional.empty(), REQUEST_STATUS.TIMEOUT, (ScheduledFuture<?>) this);
                return;
            }
            if(taxiResponse.isRejected()) {
                processResponse(taxiDTOAtomicReference, Optional.empty(), REQUEST_STATUS.REJECTED, (ScheduledFuture<?>) this);
            }
        }, 0, 2);


        ScheduledFuture<?> periodTimeOut = scheduler.schedulePeriod(() -> {
            mediator.updateStatus(REQUEST_STATUS.TIMEOUT, notificationDTOAtomicReference.get().getId());
            log.info("Wait response timeout...");
            closeScheduledFuture((ScheduledFuture<?>) this);
        }, 10, 10);

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

    private void processResponse(AtomicReference<Optional<TaxiDTO>> atomicReference, Optional<TaxiDTO> response, REQUEST_STATUS status, ScheduledFuture<?> schedule) {
        log.info("Road was " + status.name().toLowerCase());
        atomicReference.set(response);
        if(response.isPresent()) {
            closeScheduledFuture(schedule);
        }
    }

    private void closeScheduledFuture(ScheduledFuture<?> schedule) {
        schedule.cancel(false);
    }

    private void sendNotification(TaxiDTO taxiDTO, ClientDTO clientDTO, AtomicReference<NotificationDTO> notificationDTOAtomicReference) {
        notificationDTOAtomicReference.set(mediator.send(NotificationDTO.builder()
                .title("New road message!!")
                .message("A new client want a road!!")
                .taxiDTO(taxiDTO)
                .clientDTO(clientDTO)
                .build()));

    }
}
