package com.taxi.exceptions;

public class FareNotFoundException extends UseCaseException {
    public FareNotFoundException(String message) {
        super(message);
    }
    public FareNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public FareNotFoundException() {
        super("Fare not found");
    }
}
