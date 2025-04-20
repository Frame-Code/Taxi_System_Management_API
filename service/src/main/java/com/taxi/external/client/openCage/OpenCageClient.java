package com.taxi.external.client.openCage;

import DTO.LocationDTO;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log
public class OpenCageClient implements IOpenCageClient {
    @Value("${API_KEY_OPEN_CAGE}")
    private static String API_KEY;
    private final JOpenCageGeocoder jOpenCageGeocoder;

    public OpenCageClient() {
        this.jOpenCageGeocoder = new JOpenCageGeocoder(API_KEY);
    }

    @Override
    public Optional<LocationDTO> reverse_geocoding(double latitude, double longitude) {
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(latitude, longitude);
        request.setLanguage("es");
        request.setLimit(5);
        request.setNoAnnotations(true);
        request.setMinConfidence(3);

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

        if(response.getResults().isEmpty()) {
            return Optional.empty();
        }

        String city = response.getResults().get(0).getComponents().getCity();
        String province = response.getResults().get(0).getComponents().getState();

        return Optional.of(new LocationDTO(city, province));
    }
}
