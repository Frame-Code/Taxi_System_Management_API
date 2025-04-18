package com.controllers;

import DTO.BaseResponse;
import DTO.CoordinatesDTO;
import DTO.LocationDTO;
import com.taxi.service.interfaces.IParseCoordinatesService;
import com.taxi.service.interfaces.IVerifyLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/search_taxi")
@RequiredArgsConstructor
public class SearchTaxiController {
    private final IParseCoordinatesService parseCoordinatesService;
    private final IVerifyLocationService verifyLocationService;

    @GetMapping(value = "/verify_location")
    public ResponseEntity<?> verifyLocation(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "reference", required = false) String reference) {
        
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO(latitude, longitude, reference);
        Optional<LocationDTO> locationOpt = parseCoordinatesService.parseCoordinatesToLocation(coordinatesDTO);

        return locationOpt.map(locationDTO -> verifyLocationService.isLocationAvailable(locationDTO) ?
                ResponseEntity.ok(BaseResponse.builder()
                        .status_code("200")
                        .status_message("Successfully")
                        .message("Location is available")
                        .timeStamp(LocalDateTime.now())
                        .build()) :
                ResponseEntity.ok(BaseResponse.builder()
                        .status_code("204")
                        .status_message("Successfully")
                        .message("The system has not support the location provided")
                        .timeStamp(LocalDateTime.now())
                        .build())
        ).orElseGet(() ->
                ResponseEntity.ok(BaseResponse.builder()
                        .status_code("204")
                        .status_message("Successfully")
                        .message("Coordinates not founded")
                        .timeStamp(LocalDateTime.now())
                        .build()));
    }
}
