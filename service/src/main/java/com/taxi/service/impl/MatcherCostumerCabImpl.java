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
    private final AtomicInteger iterable = new AtomicInteger(0);
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
    public Optional<TaxiDTO> notifyEachCab() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        AtomicReference<ScheduledFuture<?>> futureRef = new AtomicReference<>();

        RoadNotification notification = roadNotificationService.send(
                "Request road",
                clientDTO.toString(),
                clientDTO.id(),
                taxiDTOList.get(iterable.get()).id()
        );

        ScheduledFuture<?> verifyRequestAccepted = executor.scheduleAtFixedRate(
            () -> {
                try {
                    var roadNotificationOpt = roadNotificationService.findById(notification.getId());
                    boolean ifPresent = roadNotificationOpt
                            .map(roadNotification -> roadNotification.getStatus().equals(REQUEST_STATUS.ACCEPTED))
                            .orElse(false);

                    if(ifPresent) {
                        log.info("Cab accepted the road!");
                        cabToAccepted.set(roadNotificationOpt
                                .map(RoadNotification::getTaxi)
                                .map(TaxiMapper.INSTANCE::toDTO));
                        ScheduledFuture<?> task = futureRef.get();
                        if(task != null) {
                            task.cancel(false);
                        }
                    }
                    log.info("Cab doesn't accept the road");

                    iterable.incrementAndGet();
                } catch (Exception e) {
                    log.warn("Exception matching client and cab ex: " + e.getMessage());
                }
            }, 0, RANGE, TimeUnit.SECONDS
        );
        futureRef.set(verifyRequestAccepted);

        executor.schedule(
                () -> {
                    log.warn("Maximum waiting time reached to accept the request");
                    verifyRequestAccepted.cancel(false);
                    executor.shutdown();

                    try {
                        if(!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                            log.warn("Finishing all pending threads to waiting the request road accept");
                            executor.shutdownNow();
                            log.warn("all threads finished to waiting the request road accept");
                        } else {
                            log.warn("all threads finished to waiting the request road accept");
                        }
                    } catch (InterruptedException e) {
                        executor.shutdownNow();
                        Thread.currentThread().interrupt();
                    }
                }, TOTAL_DURATION, TimeUnit.SECONDS
        );
        return cabToAccepted.get();
    }
}
