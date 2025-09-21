package com.taxi.exceptions;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
    public CityNotFoundException() {
        super("City not found");
    }
}
