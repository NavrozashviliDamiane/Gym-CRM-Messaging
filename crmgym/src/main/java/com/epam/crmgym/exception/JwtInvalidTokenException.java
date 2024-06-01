package com.epam.crmgym.exception;

public class JwtInvalidTokenException extends Exception {
    public JwtInvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}

