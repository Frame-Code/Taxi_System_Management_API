package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.PaymentNotFoundException;
import com.taxi.service.interfaces.ride_module.IPaymentService;
import dto.ClientDTO;
import dto.UserDTO;
import dto.http.request.SavePaymentRequestDto;
import dto.http.response.SavePaymentResponseDto;
import io.github.frame_code.domain.entities.Payment;
import io.github.frame_code.domain.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@CommonsLog
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;

    @Override
    public SavePaymentResponseDto save(SavePaymentRequestDto payment, ClientDTO userCreator) {
        Payment paymentEntity = Payment.builder()
                .paymentMethod(payment.paymentMethod())
                .amount(payment.amount())
                .transactionCode(UUID.randomUUID())
                .createdBy(userCreator.getInfoAuditory())
                .createdAt(LocalDateTime.now())
                .build();

        Payment paymentSaved = paymentRepository.save(paymentEntity);
        return new SavePaymentResponseDto(paymentEntity.getId(), paymentSaved.getPaymentMethod(), paymentSaved.getAmount());
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment findToGenerateRide(Long id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }
}
