package com.taxi.exceptions;

public class InvalidStatusCabException extends RuntimeException {
    public InvalidStatusCabException(String message) {
        super(message);
    }
    public InvalidStatusCabException() {
    super("Cab status invalid to set");
  }
}
