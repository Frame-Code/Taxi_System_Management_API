package com.controllers;

import DTO.LocationDTO;
import com.taxi.service.ISearchTaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController(value = "/api/search_taxi")
@RequiredArgsConstructor
public class SearchTaxiController {
    private final ISearchTaxiService searchTaxiService;

    @GetMapping(value = "/verify")
    public ResponseEntity<?> verifyLocation(@RequestParam LocationDTO locationDTO) {
        if(searchTaxiService.isPlaceAvailable(locationDTO)) {
            re
        }
        return null;
    }

}
