package com.taxi.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("Fatal error: Client not found");
    }
}
