package com.exceptionHandler;

import com.taxi.exceptions.*;
import dto.http.HttpBaseResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@CommonsLog
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(PaymentNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", ex.getMessage());
    }

    @ExceptionHandler(FareNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(FareNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", ex.getMessage());
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(ClientNotFoundException ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "500", ex.getMessage());
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(CityNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", ex.getMessage());
    }
    @ExceptionHandler(CabNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(CabNotFoundException ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "404", ex.getMessage());
    }
    @ExceptionHandler(RideStatusNotFoundException.class)
    public ResponseEntity<HttpBaseResponse> handle(RideStatusNotFoundException ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "500", ex.getMessage());
    }
    @ExceptionHandler(InvalidStatusCabException.class)
    public ResponseEntity<HttpBaseResponse> handle(InvalidStatusCabException ex) {
        return generateHandler(ex, HttpStatus.CONFLICT, null, ex.getMessage(), "409", ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpBaseResponse> handle(Exception ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, "500", ex.getMessage());
    }

    private ResponseEntity<HttpBaseResponse> generateHandler(Exception ex, HttpStatus status, String response, String message, String statusCode, String statusMessage) {
        log.warn(ex.getClass().getName() + ": " + ex.getMessage() + ". Stack trace: " + Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.status(status)
                .body(HttpBaseResponse.builder()
                        .response(response)
                        .message(message)
                        .status_code(statusCode)
                        .status_message(statusMessage)
                        .timeStamp(LocalDateTime.now())
                        .build()
                );
    }
}
