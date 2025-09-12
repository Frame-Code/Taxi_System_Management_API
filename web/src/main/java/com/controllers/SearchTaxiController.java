package com.controllers;

import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import dto.*;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CommonsLog
@RestController
@RequestMapping("/api/cabs")
@AllArgsConstructor
@NoArgsConstructor
public class SearchTaxiController {
    @Qualifier("byDistance")
    @Autowired
    private AbstractSearchCabFactory abstractSearchCabFactory;

    @Autowired
    private IMatchMediator mediator;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(value = "/search_cab")
    public ResponseEntity<BaseResponse> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        AbstractSearchCab abstractSearchCab = abstractSearchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        var listTaxi = abstractSearchCab.findCabs();
        if(listTaxi.isEmpty()) return ResponseEntity.ok(BaseResponse.builder()
                        .response(null)
                        .message("No cabs founded nearby from you")
                        .timeStamp(LocalDateTime.now())
                        .status_message("Successfully")
                        .status_code("200")
                        .build());

        for (TaxiDTO taxiDTO : listTaxi) {
            var taxiOpt = mediator.match(taxiDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
            if( taxiOpt.isPresent()) {
                log.info("The road was accepted by a Cab");
                return ResponseEntity.ok(BaseResponse.builder()
                                .response(taxiOpt.get())
                                .status_code("200")
                                .status_message("Successfully")
                                .message("The road was accepted by this cab")
                                .timeStamp(LocalDateTime.now())
                                .build());
            }
        }
        return ResponseEntity.ok(BaseResponse.builder()
                        .response(null)
                        .status_code("200")
                        .status_message("Successfully")
                        .message("No cabs accepted the road")
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

}
