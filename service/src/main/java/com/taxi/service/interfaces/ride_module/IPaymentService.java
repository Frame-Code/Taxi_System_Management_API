package com.taxi.service.interfaces.ride_module;

import io.github.frame_code.domain.entities.Payment;

import java.util.Optional;

public interface IPaymentService {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    Payment findToGenerateRide(Long id);
}
