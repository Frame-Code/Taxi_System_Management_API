package com.utils;

import dto.http.HttpBaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseUtils {

    public static  <T> ResponseEntity<HttpBaseResponse> buildResponse(String message, HttpStatus status, T response) {
        return ResponseEntity
                .status(status)
                .body(HttpBaseResponse.builder()
                        .status_code(String.valueOf(status))
                        .response(response)
                        .message(message)
                        .status_message(status.getReasonPhrase())
                        .build());
    }
}
