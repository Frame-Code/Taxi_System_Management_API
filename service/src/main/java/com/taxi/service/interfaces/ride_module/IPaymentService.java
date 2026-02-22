package com.taxi.service.interfaces.ride_module;

import dto.ClientDTO;
import dto.UserDTO;
import dto.http.request.SavePaymentRequestDto;
import dto.http.response.SavePaymentResponseDto;
import io.github.frame_code.domain.entities.Payment;

import java.util.Optional;

public interface IPaymentService {
    SavePaymentResponseDto save(SavePaymentRequestDto payment, ClientDTO userCreator);
    Optional<Payment> findById(Long id);
    Payment findToGenerateRide(Long id);
}
