package com.epam.crmgym.exception;

public class UsernameValidationException extends Exception {

    public UsernameValidationException(String message) {
        super(message);
    }

    public UsernameValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
