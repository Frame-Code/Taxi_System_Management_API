package com.taxi.exceptions;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException(String message) {
        super(message);
    }
    public RideNotFoundException() {
        super("Ride not found");
    }
}
