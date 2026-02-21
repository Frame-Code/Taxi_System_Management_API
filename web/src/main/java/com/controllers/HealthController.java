package com.controllers;

import dto.http.HttpBaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<HttpBaseResponse> getHealth() {
        HttpBaseResponse response = HttpBaseResponse
                .builder()
                .response(null)
                .status_code("200")
                .status_message("Stin City Taxi API is running")
                .build();
        return ResponseEntity.ok(response);
    }

}
