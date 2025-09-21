package com.taxi.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
    public PaymentNotFoundException() {
        super("Fatal error: Payment was not found");
    }
}
