package com.controllers;

import dto.http.HttpBaseResponse;
import dto.CoordinatesDTO;
import dto.LocationDTO;
import com.taxi.service.interfaces.location_module.IParseCoordinatesService;
import com.taxi.service.interfaces.location_module.IVerifyLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final IParseCoordinatesService parseCoordinatesService;
    private final IVerifyLocationService verifyLocationService;

    @GetMapping(value = "/verify")
    public ResponseEntity<HttpBaseResponse> verifyLocation(
            @RequestParam("latitude") final Double latitude,
            @RequestParam("longitude") final Double longitude) {
        
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO(latitude, longitude);
        Optional<LocationDTO> locationOpt = parseCoordinatesService.parseCoordinatesToLocation(coordinatesDTO);

        if (locationOpt.isEmpty()) {
            return ResponseEntity.status(404).body(HttpBaseResponse.builder()
                    .status_code("404")
                    .response(coordinatesDTO)
                    .status_message("Error")
                    .message("Ubicación solicitada no encontrada.\nIntente una ubicación distinta...")
                    .timeStamp(LocalDateTime.now())
                    .build());
        }

        Optional<LocationDTO> locationDTO = verifyLocationService.isLocationAvailable(locationOpt.get());
        if (locationDTO.isEmpty()) {
            return ResponseEntity.status(422).body(HttpBaseResponse.builder()
                    .status_code("422")
                    .response(coordinatesDTO)
                    .status_message("Unavailable")
                    .message("El sistema no soporta la ubicación solicitada\nIntente una ubicación distinta...")
                    .timeStamp(LocalDateTime.now())
                    .build());
        }

        return ResponseEntity.ok(HttpBaseResponse.builder()
                .status_code("200")
                .response(locationDTO)
                .status_message("Successfully")
                .message("Location is available")
                .timeStamp(LocalDateTime.now())
                .build());

    }
}
