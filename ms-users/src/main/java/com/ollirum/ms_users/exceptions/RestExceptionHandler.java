package com.ollirum.ms_users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}