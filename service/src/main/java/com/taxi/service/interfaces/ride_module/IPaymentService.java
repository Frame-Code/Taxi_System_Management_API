package com.taxi.service.interfaces.ride_module;

import dto.http.PaymentRequestDto;
import dto.http.PaymentResponseDto;
import io.github.frame_code.domain.entities.Payment;

import java.util.Optional;

public interface IPaymentService {
    PaymentResponseDto save(PaymentRequestDto payment);
    Optional<Payment> findById(Long id);
    Payment findToGenerateRide(Long id);
}
