package com.taxi.service.interfaces.fare_module;

import io.github.frame_code.domain.entities.Fare;

import java.util.Optional;

public interface IFareService {
    Fare save(Fare fare);
    Optional<Fare> find();
    Fare update(Fare fare);
}
