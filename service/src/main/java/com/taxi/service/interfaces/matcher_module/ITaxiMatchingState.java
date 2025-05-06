package com.taxi.service.interfaces.matcher_module;

import dto.TaxiDTO;

import java.util.Optional;

public interface ITaxiMatchingState {
    Optional<TaxiDTO> getResult();
}
