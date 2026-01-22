package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.PaymentNotFoundException;
import com.taxi.service.interfaces.ride_module.IPaymentService;
import dto.PaymentDTO;
import dto.http.PaymentRequestDto;
import dto.http.PaymentResponseDto;
import io.github.frame_code.domain.entities.Payment;
import io.github.frame_code.domain.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@CommonsLog
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto save(PaymentRequestDto payment) {
        Payment paymentEntity = Payment.builder()
                .paymentMethod(payment.paymentMethod())
                .amount(payment.amount())
                .transactionCode(UUID.randomUUID())
                .build();

        Payment paymentSaved = paymentRepository.save(paymentEntity);
        return new PaymentResponseDto(paymentEntity.getId(), paymentSaved.getPaymentMethod(), paymentSaved.getAmount());
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
