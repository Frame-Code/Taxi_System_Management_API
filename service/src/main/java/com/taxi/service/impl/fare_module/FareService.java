package com.taxi.service.impl.fare_module;

import com.taxi.service.interfaces.fare_module.IFareService;
import io.github.frame_code.domain.entities.Fare;
import io.github.frame_code.domain.repository.IFareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class FareService implements IFareService {
    private final IFareRepository repository;

    @Override
    public Fare save(Fare fare) {
        return repository.save(fare);
    }

    @Override
    public Optional<Fare> find() {
        return repository.find();
    }

    @Override
    public Fare update(Fare fare) {
        return repository.save(fare);
    }
}
