package com.controllers;

import DTO.CoordinatesDTO;
import DTO.SearchCabDTO;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.SearchCab;
import com.taxi.service.SearchCabFactory;
import com.taxi.service.interfaces.MatcherCostumerCab;
import io.github.frame_code.domain.repository.ClientRepository;
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
public class SearchTaxiController {
    @Qualifier("byDistance")
    @Autowired
    private SearchCabFactory searchCabFactory;

    @Autowired
    private MatcherCostumerCab matcherCostumerCab;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<?> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        SearchCab searchCab = searchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        var listTaxi = searchCab.findCabs();
        matcherCostumerCab.setCabsToNotify(listTaxi);
        matcherCostumerCab.setClientToMatch(ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
        return ResponseEntity.ok(matcherCostumerCab.notifyEachCab());
    }
}
