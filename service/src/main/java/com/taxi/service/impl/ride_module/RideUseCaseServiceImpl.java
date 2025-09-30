package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import com.taxi.service.interfaces.ride_module.*;
import dto.AcceptRoadDTO;
import dto.ClientDTO;
import dto.UserAuditoryDTO;
import io.github.frame_code.domain.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import utils.AuditoryUtils;
import utils.GeolocationUtils;

import java.time.LocalDateTime;

@Service
@CommonsLog
@RequiredArgsConstructor
public class RideUseCaseServiceImpl implements IRideUseCaseService {
    private final IRideService service;
    private final ICabService cabService;
    private final IPaymentService paymentService;
    private final ICityService cityService;
    private final IClientService clientService;

    @Override
    public void acceptRoad(AcceptRoadDTO roadDTO, ClientDTO clientDTO) {
        Payment payment = paymentService.findToGenerateRide(roadDTO.idPayment());
        Taxi taxi = cabService.findToGenerateRide(roadDTO.idTaxi());
        City cityOrigin = cityService.findToGenerateRide(roadDTO.idCityOrigin());
        City cityDestiny = cityService.findToGenerateRide(roadDTO.idCityDestiny());
        Client client = clientService.findToGenerateRide(clientDTO.id());
        Point origin = GeolocationUtils.createPoint(roadDTO.coordinatesDTO().origin().latitude(), roadDTO.coordinatesDTO().origin().longitude());
        Point destiny = GeolocationUtils.createPoint(roadDTO.coordinatesDTO().destiny().latitude(), roadDTO.coordinatesDTO().destiny().longitude());
        UserAuditoryDTO userAuditoryDTO = new UserAuditoryDTO(client.getUser().getFullNames(), client.getUser().getRole().getRoleName().toString());
        RoadAddress startAddress = service.save(RoadAddress.builder()
                .location(origin)
                .city(cityOrigin)
                .build());
        RoadAddress endAddress = service.save(RoadAddress.builder()
                .location(destiny)
                .city(cityDestiny)
                .build());

        Road road = Road.builder()
                .status(STATUS_ROAD.INITIALIZED)
                .payment(payment)
                .taxi(taxi)
                .client(client)
                .createdBy(AuditoryUtils.createCreatedAt(userAuditoryDTO))
                .startAddress(startAddress)
                .endAddress(endAddress)
                .startDate(LocalDateTime.now())
                .build();
        service.save(road);
        log.info("Road saved correctly");
    }
}
