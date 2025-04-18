package com.taxi.external.service;

import DTO.LocalityDTO;

import java.util.Optional;

public interface IOpenCageService {
    Optional<LocalityDTO> getLocalityFromResponse(String response);
}
