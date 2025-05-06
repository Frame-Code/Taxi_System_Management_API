package com.controllers;

import dto.CoordinatesDTO;
import dto.SearchCabDTO;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import com.taxi.service.interfaces.matcher_module.IMatcherCostumerCab;
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
    private IMatcherCostumerCab IMatcherCostumerCab;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<?> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        AbstractSearchCab abstractSearchCab = abstractSearchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        var listTaxi = abstractSearchCab.findCabs();
        IMatcherCostumerCab.setCabsToNotify(listTaxi);
        IMatcherCostumerCab.setClientToMatch(ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
        return ResponseEntity.ok(IMatcherCostumerCab.notifyEachCab());
    }
}
