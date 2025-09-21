package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_ROAD;
import com.taxi.service.interfaces.ride_module.*;
import dto.AcceptRoadDTO;
import io.github.frame_code.domain.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
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

    @Override
    public Road acceptRoad(AcceptRoadDTO roadDTO) {
        Payment payment = paymentService.findToGenerateRide(roadDTO.idPayment());
        Taxi taxi = cabService.findToGenerateRide(roadDTO.idTaxi());
        City cityOrigin = cityService.findToGenerateRide(roadDTO.idCityOrigin());
        City cityDestiny = cityService.findToGenerateRide(roadDTO.idCityDestiny());
        Point origin = GeolocationUtils.createPoint(roadDTO.coordinatesDTO().origin().latitude(), roadDTO.coordinatesDTO().origin().longitude());
        Point destiny = GeolocationUtils.createPoint(roadDTO.coordinatesDTO().destiny().latitude(), roadDTO.coordinatesDTO().destiny().longitude());

        Road road = Road.builder()
                .status(STATUS_ROAD.INITIALIZED)
                .payment(payment)
                .taxi(taxi)
                .startAddress(RoadAddress.builder()
                        .location(origin)
                        .city(cityOrigin)
                        .build())
                .endAddress(RoadAddress.builder()
                        .location(destiny)
                        .city(cityDestiny)
                        .build())
                .startDate(LocalDateTime.now())
                .build();
        return service.save(road);
    }
}
