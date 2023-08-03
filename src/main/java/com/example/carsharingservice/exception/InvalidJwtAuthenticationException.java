package com.example.carsharingservice.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String message, Throwable e) {
        super(message, e);
    }
}

