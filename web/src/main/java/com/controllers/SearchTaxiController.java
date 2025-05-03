package com.controllers;

import DTO.CoordinatesDTO;
import DTO.SearchCabDTO;
import com.taxi.service.SearchCab;
import com.taxi.service.SearchCabFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search_taxi")
public class SearchTaxiController {
    @Qualifier("byDistance")
    @Autowired
    private SearchCabFactory searchCabFactory;

    @PostMapping
    public ResponseEntity<?> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        SearchCab searchCab = searchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        return ResponseEntity.ok(searchCab.findCabs());
    }
}
