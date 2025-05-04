package com.taxi.service.impl;

import DTO.ClientDTO;
import DTO.TaxiDTO;
import Enums.entitiesEnums.REQUEST_STATUS;
import com.taxi.mappers.TaxiMapper;
import com.taxi.service.interfaces.IRoadNotificationService;
import com.taxi.service.interfaces.MatcherCostumerCab;
import io.github.frame_code.domain.entities.RoadNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@CommonsLog
@Service
@RequiredArgsConstructor
public class MatcherCostumerCabImpl implements MatcherCostumerCab {
    private List<TaxiDTO> taxiDTOList;
    private ClientDTO clientDTO;
    private final IRoadNotificationService roadNotificationService;
    private final int TOTAL_DURATION = 20;
    private final int RANGE = 2;
    private final AtomicReference<Optional<TaxiDTO>> cabToAccepted = new AtomicReference<>();

    @Override
    public void setCabsToNotify(List<TaxiDTO> taxiDTO) {
        this.taxiDTOList = taxiDTO;
    }

    @Override
    public void setClientToMatch(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    @Override
    public void attach(TaxiDTO taxiDTO) {
        try {
            taxiDTOList.add(taxiDTO);
        } catch (RuntimeException e) {
            log.warn("List of cabs not specified");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void detach(Long id) {
        try {
            taxiDTOList.removeIf(taxiDTO -> taxiDTO.id().equals(id));
        } catch (RuntimeException e) {
            log.warn("List of cabs not specified");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<TaxiDTO> notifyEachCab() throws NullPointerException {
        int MAX_ATTEMPTS = taxiDTOList.size();
        log.info("MAXIMUM ATTEMPTS: " + MAX_ATTEMPTS);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        final AtomicReference<ScheduledFuture<?>> futureRef = new AtomicReference<>();
        final AtomicInteger attemptCount = new AtomicInteger(0);
        final AtomicBoolean isTaskCompleted = new AtomicBoolean(false);
        final AtomicReference<RoadNotification> roadNotificationAtomicReference = new AtomicReference<>();

        Runnable sendAndVerifyRequest = () -> {
            if (isTaskCompleted.get() || attemptCount.get() >= MAX_ATTEMPTS) {
                executor.shutdown();
                log.info("Finalizing Tasks to match a client with a cab");
                return;
            }
            ScheduledFuture<?> verifyRequestAccepted = executor.scheduleAtFixedRate(() -> {
                        if (isTaskCompleted.get()) {
                            ScheduledFuture<?> task = futureRef.get();
                            if (task != null) {
                                log.warn("Attempt task: " + attemptCount.get() + "cancelled");
                                task.cancel(false);
                            }
                            log.warn("Attempt task: " + attemptCount.get() + "completed");
                            return;
                        }
                        try {
                            var roadNotificationOpt = roadNotificationService.findById(roadNotificationAtomicReference.get().getId());
                            boolean isAccepted = roadNotificationOpt
                                    .map(roadNotification -> roadNotification.getStatus().equals(REQUEST_STATUS.ACCEPTED))
                                    .orElse(false);
                            boolean isRejected = roadNotificationOpt
                                    .map(roadNotification -> roadNotification.getStatus().equals(REQUEST_STATUS.REJECTED))
                                    .orElse(false);

                            if (isAccepted) {
                                log.info("Cab accepted the road!");
                                isTaskCompleted.set(true);
                                cabToAccepted.set(roadNotificationOpt
                                        .map(RoadNotification::getTaxi)
                                        .map(TaxiMapper.INSTANCE::toDTO));
                                ScheduledFuture<?> task = futureRef.get();
                                if (task != null) {
                                    task.cancel(false);
                                    log.info("Task matching finished");
                                }
                                executor.shutdown();
                                return;
                            }

                            if(isRejected) {
                                log.info("Cab rejected the road!");
                                ScheduledFuture<?> task = futureRef.get();
                                if (task != null) {
                                    task.cancel(false);
                                    log.info("Task matching finished");
                                }
                                return;
                            }

                            log.info("Cab doesn't accept the road");
                        } catch (Exception e) {
                            log.warn("Exception matching client and cab ex: " + e.getMessage());
                        }
                    }, 0, RANGE, TimeUnit.SECONDS
            );
            futureRef.set(verifyRequestAccepted);

            //Finalizar ciclo de busqueda despues de TOTAL_DURATION
            executor.schedule(() -> {
                log.warn("End attempt #" + attemptCount.get());
                if (!isTaskCompleted.get() && attemptCount.get() <= MAX_ATTEMPTS) {
                    attemptCount.incrementAndGet();
                    roadNotificationService.updateStatus(REQUEST_STATUS.TIMEOUT, roadNotificationAtomicReference.get().getId());
                    roadNotificationAtomicReference.set(
                            roadNotificationService.send(
                                    "Request road",
                                    clientDTO.toString(),
                                    clientDTO.id(),
                                    taxiDTOList.get(attemptCount.get()).id())
                    );
                    log.info("Starting a new matching between cab with a client, attempt: " + attemptCount.get());
                    executor.schedule((Runnable) this, 0, TimeUnit.SECONDS);
                } else if (!isTaskCompleted.get()) {
                    log.warn("Maximum attempts reached, any cab accept this road");
                    executor.shutdown();

                }
            }, TOTAL_DURATION, TimeUnit.SECONDS);
        };

        //INICIA EL PRIMER CICLO
        executor.schedule(() -> {
            roadNotificationAtomicReference.set(
                    roadNotificationService.send(
                            "Request road",
                            clientDTO.toString(),
                            clientDTO.id(),
                            taxiDTOList.get(attemptCount.get()).id())
            );
            log.info("Starting a new matching between cab with a client, attempt: " + attemptCount.get());
            sendAndVerifyRequest.run();
        }, 0, TimeUnit.SECONDS);

        try {
            executor.awaitTermination((long) MAX_ATTEMPTS * TOTAL_DURATION + 10, TimeUnit.SECONDS);
            log.info("All task ended");
        } catch (InterruptedException e) {
            log.warn("Interrupted task finishing");
            Thread.currentThread().interrupt();
        }

        if (!executor.isShutdown()) {
            executor.shutdownNow();
            log.info("ScheduleExecutorService finished");
        }

        log.info("Returning the value: " + cabToAccepted.get());
        return cabToAccepted.get();
    }
}
