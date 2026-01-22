package com.controllers;

import com.taxi.service.interfaces.ride_module.IPaymentService;
import dto.BaseResponse;
import dto.http.PaymentRequestDto;
import dto.http.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;

    @PostMapping()
    public ResponseEntity<BaseResponse> save(@NotNull @RequestBody final PaymentRequestDto request) {
        PaymentResponseDto response = paymentService.save(request);
        return ResponseEntity.ok(
                BaseResponse.builder()
                        .response(response)
                        .status_code("200")
                        .status_message("Payment created successfully")
                        .build());
    }

}
