package com.controllers;

import com.taxi.service.interfaces.matcher_module.IMatchMediator;
import com.taxi.service.interfaces.ride_module.IRideService;
import com.taxi.service.interfaces.ride_module.IRideUseCaseService;
import dto.*;
import com.taxi.mappers.ClientMapper;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCab;
import com.taxi.service.abstracts.find_cabs_module.AbstractSearchCabFactory;
import io.github.frame_code.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@CommonsLog
@RestController
@RequestMapping("/api/cabs")
@AllArgsConstructor
@NoArgsConstructor
public class MatchCabController {
    @Qualifier("byDistance")
    @Autowired
    private AbstractSearchCabFactory abstractSearchCabFactory;

    @Autowired
    private IMatchMediator mediator;

    @Autowired
    private IRideService rideService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private IRideUseCaseService rideUseCaseService;

    @PostMapping(value = "/search_cab")
    public ResponseEntity<BaseResponse> searchCabs(@RequestBody final CoordinatesDTO coordinatesDTO) {
        AbstractSearchCab abstractSearchCab = abstractSearchCabFactory.createSearchCab(new SearchCabDTO(coordinatesDTO));
        var listTaxi = abstractSearchCab.findCabs();
        if(listTaxi.isEmpty()) return ResponseEntity.ok(BaseResponse.builder()
                        .response(null)
                        .message("No cabs founded nearby from you")
                        .timeStamp(LocalDateTime.now())
                        .status_message("Successfully")
                        .status_code("200")
                        .build());

        for (TaxiDTO taxiDTO : listTaxi) {
            var taxiOpt = mediator.match(taxiDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get()));
            if( taxiOpt.isPresent()) {
                log.info("The road was accepted by a Cab");
                return ResponseEntity.ok(BaseResponse.builder()
                                .response(taxiOpt.get())
                                .status_code("200")
                                .status_message("Successfully")
                                .message("The road was accepted by this cab")
                                .timeStamp(LocalDateTime.now())
                                .build());
            }
        }
        return ResponseEntity.ok(BaseResponse.builder()
                        .response(null)
                        .status_code("200")
                        .status_message("Successfully")
                        .message("No cabs accepted the road")
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    @GetMapping(value = "/get_info_ride")
    public ResponseEntity<BaseResponse> getInfoRide(@RequestBody final FullCoordinatesDTO coordinatesDTO) {
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

    //El cliente acepta la ruta y se tiene que guardar en road la nueva ruta inicializada
    //Crear controller advice para gestionar las exepciones
    @PostMapping(value = "/accept_road")
    public ResponseEntity<BaseResponse> acceptRoad(@RequestBody AcceptRoadDTO acceptRoadDTO)  {
        return ResponseEntity.ok(BaseResponse.builder()
                        .response(rideUseCaseService.acceptRoad(acceptRoadDTO, ClientMapper.INSTANCE.toClientDTO(clientRepository.findById(1L).get())))
                        .message("The road was accepted")
                        .status_message("Successfully")
                        .timeStamp(LocalDateTime.now())
                        .status_code("200")
                .build());
    }

}
