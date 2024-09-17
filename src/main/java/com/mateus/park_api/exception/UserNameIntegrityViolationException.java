package com.mateus.park_api.exception;

public class UserNameIntegrityViolationException extends RuntimeException {
    public UserNameIntegrityViolationException(String message) {
        super(message);
    }
}
