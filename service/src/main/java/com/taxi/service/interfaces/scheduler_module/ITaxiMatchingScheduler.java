package com.taxi.service.interfaces.scheduler_module;

import dto.ClientDTO;
import dto.TaxiDTO;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ITaxiMatchingScheduler {
    CompletableFuture<Optional<TaxiDTO>> match(ClientDTO clientDTO, TaxiDTO taxiDTO);
}
