package com.taxi.service.impl.scheduler_module;

import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import com.taxi.service.interfaces.scheduler_module.IMatchingScheduler;
import com.taxi.service.interfaces.scheduler_module.ITaxiMatchingScheduler;
import dto.ClientDTO;
import dto.NotificationDTO;
import dto.TaxiDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@CommonsLog
@Service
@RequiredArgsConstructor
public class TaxiMatchingSchedulerImpl implements ITaxiMatchingScheduler {
    private final IMatchingScheduler scheduler;
    private final IMatchMediator mediator;

    @Override
    public CompletableFuture<Optional<TaxiDTO>> match(ClientDTO clientDTO, TaxiDTO taxiDTO) {
    /*    final AtomicReference<Optional<TaxiDTO>> taxiDTOAtomicReference = new AtomicReference<>();
        final AtomicReference<NotificationDTO> notificationDTOAtomicReference = new AtomicReference<>();
        notificationDTOAtomicReference.set(mediator.send(NotificationDTO.builder()
                .title("New road message!!")
                .message("A new client want a road!!")
                .taxiDTO(taxiDTO)
                .clientDTO(clientDTO)
                .build()));

        scheduler.schedulePeriod(() -> {
            var taxiResponse = mediator.getResponse(notificationDTOAtomicReference.get().getId());

            if (taxiResponse.isAccepted() || taxiResponse.isRejected()) {
                processResponse(taxiDTOAtomicReference, Optional.of(taxiDTO));
            }
            if(taxiResponse.isRejected()) {
                processResponse(taxiDTOAtomicReference, Optional.empty());
            }






        }, 0, 2);


        return CompletableFuture<taxiDTOAtomicReference.get()>;*/
        return null;
    }

    private void processResponse(AtomicReference<Optional<TaxiDTO>> atomicReference, Optional<TaxiDTO> response) {
        atomicReference.set(response);
        scheduler.shutdownNow();
        log.info("Shutting down the scheduler, road accepted");
    }
}
