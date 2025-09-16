package com.taxi.service.impl.ride_module;

import Enums.entitiesEnums.STATUS_TAXI;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.taxi.exceptions.FareNotFoundException;
import com.taxi.external.client.openRouteService.IOpenRouteServiceClient;
import com.taxi.service.interfaces.ride_module.IRideService;
import dto.FullCoordinatesDTO;
import dto.RideInfoDTO;
import io.github.frame_code.domain.entities.Road;
import io.github.frame_code.domain.repository.IFareRepository;
import io.github.frame_code.domain.repository.IRoadRepository;
import io.github.frame_code.domain.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CommonsLog
@Service
@RequiredArgsConstructor
public class RideServiceImpl implements IRideService {
    private final IFareRepository fareRepository;
    private final IRoadRepository roadRepository;
    private final IOpenRouteServiceClient openRouteServiceClient;
    private final TaxiRepository taxiRepository;
    private final Gson gson = new Gson();

    @Override
    public Road save(Road road) {
        road.getTaxi().setStatus(STATUS_TAXI.WORKING);
        taxiRepository.save(road.getTaxi());
        return roadRepository.save(road);
    }

    @Override
    public double getTotalPrice(double approxDistance, double approxSeconds) {
        log.info("Calculating price for ride...");
        return Math.round(fareRepository.find()
                .map(fare ->
                        (fare.getPricePerKm() * approxDistance) / 1000
                                + (fare.getPricePerMinute() * getMinutes(approxSeconds))
                                + (fare.getBaseFare()))
                .orElseThrow(FareNotFoundException::new));
    }

    @Override
    public Optional<RideInfoDTO> getRideInfo(FullCoordinatesDTO coordinatesDTO) throws IOException {
        String response = openRouteServiceClient.getResponse(coordinatesDTO)
                .block(Duration.ofSeconds(25));

        JsonElement rootElement = gson.fromJson(response, JsonElement.class);
        if(!rootElement.isJsonObject()) {
            return Optional.empty();
        }
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonElement rideSegments = rootObject.get("features")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("properties")
                .getAsJsonObject()
                .get("segments")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject();
        return Optional.of(
                new RideInfoDTO(
                        rideSegments.getAsJsonObject().get("distance").getAsDouble(),
                        rideSegments.getAsJsonObject().get("duration").getAsDouble()
                )
        );
    }

    private double getMinutes(double seconds) {
        return seconds / 60;
    }
}
