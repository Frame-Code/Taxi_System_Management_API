package com.controllers;

import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import dto.CoordinatesDTO;
import dto.NotificationDTO;
import dto.SearchCabDTO;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import dto.TaxiDTO;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping("/api/search_taxi")
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

    @PostMapping
    public ResponseEntity<?> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        AbstractSearchCab abstractSearchCab = abstractSearchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        var listTaxi = abstractSearchCab.findCabs();
        for (TaxiDTO taxiDTO : listTaxi) {
            var taxiOpt = mediator.match(taxiDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
            if( taxiOpt.isPresent()) {
                log.info("Cab accepted the road");
                return ResponseEntity.ok(taxiOpt.get());
            }
            log.info("end of loop");
        }
        return ResponseEntity.ok("No cabs accepted the road");
    }
}
