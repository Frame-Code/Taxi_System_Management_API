package com.taxi.exceptions;

public class CabNotFoundException extends UseCaseException{
    public CabNotFoundException(String message) {
        super(message);
    }
    public CabNotFoundException() {
        super("Fatal error: The cab was not found");
    }
}
