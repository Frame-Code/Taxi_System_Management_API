package com.taxi.service.impl.ride_module;

import com.taxi.exceptions.PaymentNotFoundException;
import com.taxi.service.interfaces.ride_module.IPaymentService;
import io.github.frame_code.domain.entities.Payment;
import io.github.frame_code.domain.repository.IPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CommonsLog
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {
    private final IPaymentRepository paymentRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
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
