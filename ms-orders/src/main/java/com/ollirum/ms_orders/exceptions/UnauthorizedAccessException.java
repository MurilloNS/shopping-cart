package com.ollirum.ms_orders.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message){
        super(message);
    }
}