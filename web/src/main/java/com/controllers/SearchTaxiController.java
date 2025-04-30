package com.controllers;

import DTO.CoordinatesDTO;
import DTO.CoordinatesToSearchDTO;
import com.taxi.service.interfaces.ISearchTaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search_taxi")
@RequiredArgsConstructor
public class SearchTaxiController {
    //private final ISearchTaxiService searchTaxiService;

    @PostMapping
    public ResponseEntity<?> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        return null;
    }
}
