package com.exceptionHandler;

import com.taxi.exceptions.*;
import dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(PaymentNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", "Error");
    }

    @ExceptionHandler(FareNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(FareNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", "Error");
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(ClientNotFoundException ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "500", "Error");
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(CityNotFoundException ex) {
        return generateHandler(ex, HttpStatus.NOT_FOUND, null, ex.getMessage(), "404", "Error");
    }
    @ExceptionHandler(CabNotFoundException.class)
    public ResponseEntity<BaseResponse> handle(CabNotFoundException ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "404", "Error");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handle(Exception ex) {
        return generateHandler(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage(), "500", "Error");
    }

    private ResponseEntity<BaseResponse> generateHandler(Exception ex, HttpStatus status, String response, String message, String statusCode, String statusMessage) {
        return ResponseEntity.status(status)
                .body(BaseResponse.builder()
                        .response(response)
                        .message(message)
                        .status_code(statusCode)
                        .status_message(statusMessage)
                        .timeStamp(LocalDateTime.now())
                        .build()
                );
    }
}
