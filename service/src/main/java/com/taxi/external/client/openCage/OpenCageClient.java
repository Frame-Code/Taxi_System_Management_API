package com.taxi.external.client.openCage;

import DTO.LocationDTO;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import com.opencagedata.jopencage.model.JOpenCageReverseRequest;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;

@Component
@Log
public class OpenCageClient implements IOpenCageClient {
    private final JOpenCageGeocoder jOpenCageGeocoder;

    public OpenCageClient() {
        this.jOpenCageGeocoder = new JOpenCageGeocoder(System.getProperty("API_KEY_OPEN_CAGE"));
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
            log.log(Level.INFO, "Coordinates not founded");
            return Optional.empty();
        }

        String city = response.getResults().get(0).getComponents().getCity();
        String province = response.getResults().get(0).getComponents().getState();

        return Optional.of(new LocationDTO(city, province));
    }
}
