package com.ollirum.ms_products.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message){
        super(message);
    }
}