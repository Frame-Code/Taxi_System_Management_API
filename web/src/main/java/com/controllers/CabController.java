package com.controllers;

import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import dto.*;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import dto.http.HttpBaseResponse;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@CommonsLog
@RestController
@RequestMapping("/api/cab")
@RequiredArgsConstructor
public class CabController {
    @Qualifier("byDistance")
    private final AbstractSearchCabFactory abstractSearchCabFactory;
    private final IMatchMediator mediator;
    private final ClientRepository clientRepository;

    @GetMapping(value = "/search")
    public ResponseEntity<HttpBaseResponse> searchCabs(
            @RequestParam("latitude") @NotNull final Double latitude,
            @RequestParam("longitude") @NotNull final Double longitude) {
        SearchCabDTO dto = new SearchCabDTO(new CoordinatesDTO(latitude, longitude));
        AbstractSearchCab abstractSearchCab = abstractSearchCabFactory.createSearchCab(dto);
        var listTaxi = abstractSearchCab.findCabs();
        if(listTaxi.isEmpty()) return ResponseEntity.ok(HttpBaseResponse.builder()
                        .response(null)
                        .message("No cabs founded nearby from you")
                        .timeStamp(LocalDateTime.now())
                        .status_message("Successfully")
                        .status_code("204")
                        .build());

        for (TaxiDTO taxiDTO : listTaxi) {
            var taxiOpt = mediator.match(taxiDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
            if( taxiOpt.isPresent()) {
                log.info("The road was accepted by a Cab");
                return ResponseEntity.ok(HttpBaseResponse.builder()
                                .response(taxiOpt.get())
                                .status_code("200")
                                .status_message("Successfully")
                                .message("The road was accepted by this cab")
                                .timeStamp(LocalDateTime.now())
                                .build());
            }
        }
        return ResponseEntity.ok(HttpBaseResponse.builder()
                        .response(null)
                        .status_code("204")
                        .status_message("Successfully")
                        .message("No cabs accepted the road")
                        .timeStamp(LocalDateTime.now())
                        .build());
    }
}
