package com.taxi.exceptions;

public class RideStatusNotFoundException extends RuntimeException {
    public RideStatusNotFoundException(String message) {
        super(message);
    }
    public RideStatusNotFoundException() {
        super("Ride status not found");
    }
}
