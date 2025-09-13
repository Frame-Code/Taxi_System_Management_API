package com.taxi.exceptions;

public abstract class UseCaseException extends RuntimeException{
    protected UseCaseException(String message) {
        super(message);
    }

    protected UseCaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
