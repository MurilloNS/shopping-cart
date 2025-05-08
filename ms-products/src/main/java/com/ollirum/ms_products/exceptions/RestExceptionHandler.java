package com.ollirum.ms_products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> orderNotFound(ProductNotFoundException e) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiError> unauthorizedAccess(UnauthorizedAccessException e) {
        ApiError apiError = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}