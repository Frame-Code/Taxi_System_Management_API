package com.taxi.external.service;

import DTO.LocalityDTO;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenCageService implements IOpenCageService{

    @Override
    public Optional<LocalityDTO> getLocalityFromResponse(String response) {
        if(!response.contains("components")) {
            return Optional.empty();
        }

        Matcher cityMatcher = Pattern.compile("\"city\"\\s*:\\s*\"([^\"]+)\"").matcher(response);
        Matcher stateMatcher = Pattern.compile("\"state\"\\s*:\\s*\"([^\"]+)\"").matcher(response);

        String city = "";
        String state = "";

        if (cityMatcher.find()) {
            city = StringEscapeUtils.unescapeJava(cityMatcher.group(1));
        }
        if (stateMatcher.find()) {
            state =  StringEscapeUtils.unescapeJava(stateMatcher.group(1));
        }

        return Optional.of(new LocalityDTO(city,state));
    }

}
