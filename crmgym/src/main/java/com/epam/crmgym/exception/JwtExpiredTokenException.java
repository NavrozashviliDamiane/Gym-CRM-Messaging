package com.epam.crmgym.exception;

public class JwtExpiredTokenException extends Exception {
    public JwtExpiredTokenException(String message) {
        super(message);
    }
}

