package com.controllers;

import com.taxi.mappers.ClientMapper;
import com.taxi.service.interfaces.ride_module.IRideService;
import com.taxi.service.interfaces.ride_module.IRideUseCaseService;
import dto.*;
import dto.request_body.SetStatusDTO;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@CommonsLog
@RestController
@RequestMapping(value = "/api/ride")
@RequiredArgsConstructor
public class RideController {
    private final IRideService rideService;
    private final IRideUseCaseService rideUseCaseService;
    private final ClientRepository clientRepository;

    @PostMapping(value = "/info")
    public ResponseEntity<BaseResponse> getInfo(@RequestBody final FullCoordinatesDTO coordinatesDTO) {
        try {
            Optional<DistanceInfoDTO> distanceInfoDTO = rideService.getRideInfo(coordinatesDTO);
            if(distanceInfoDTO.isEmpty()) return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(BaseResponse.builder()
                            .response(null)
                            .status_code("503")
                            .status_message("Error")
                            .message("Can't get the ride info...")
                            .timeStamp(LocalDateTime.now())
                            .build()
                    );

            double totalPrice = rideService.getTotalPrice(distanceInfoDTO.get().approxDistance(), distanceInfoDTO.get().approxSeconds());
            RideInfoDTO rideInfoDTO = new RideInfoDTO(distanceInfoDTO.get(), totalPrice);
            log.info("Returning ride info...");
            return ResponseEntity.ok(BaseResponse.builder()
                    .response(rideInfoDTO)
                    .status_code("200")
                    .status_message("Successfully")
                    .message("The ride info")
                    .timeStamp(LocalDateTime.now())
                    .build());
        } catch (IOException e) {
            log.error("Error, can't get the ride info");
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/start")
    public ResponseEntity<BaseResponse> startRide(@RequestBody AcceptRoadDTO acceptRoadDTO)  {
        //Cuando se habilita spring security se modificara con el id del usuario logeado
        rideUseCaseService.acceptRoad(acceptRoadDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
        return ResponseEntity.ok(BaseResponse.builder()
                .response(null)
                .message("The road was accepted")
                .status_message("Successfully")
                .timeStamp(LocalDateTime.now())
                .status_code("200")
                .build());
    }

    @PostMapping(value = "/status")
    public ResponseEntity<BaseResponse> setStatus(@RequestBody SetStatusDTO statusDTO) {
        var result = rideUseCaseService.setStatus(statusDTO.status(),statusDTO.idRide());
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
