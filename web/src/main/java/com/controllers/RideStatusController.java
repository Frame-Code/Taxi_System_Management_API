package com.controllers;

import com.taxi.service.interfaces.ride_module.ISetStatus;
import dto.BaseResponse;
import dto.request_body.SetStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CommonsLog
@RestController
@RequestMapping(value = "/api/ride_status")
@RequiredArgsConstructor
public class RideStatusController {
    private final ISetStatus setStatus;

    @PostMapping(value = "/set")
    public ResponseEntity<BaseResponse> setStatus(@RequestBody SetStatusDTO statusDTO) {
        var result = setStatus.set(statusDTO.status(),statusDTO.idRide());
        return result.isSaved()?
                ResponseEntity.ok(BaseResponse.builder()
                        .response(null)
                        .message(result.message())
                        .timeStamp(LocalDateTime.now())
                        .status_message("Successfully")
                        .status_code("200")
                        .build())
                :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.builder()
                        .response(null)
                        .message(result.message())
                        .timeStamp(LocalDateTime.now())
                        .status_message("Error: ride not found")
                        .status_code("404")
                        .build());
    }
}
