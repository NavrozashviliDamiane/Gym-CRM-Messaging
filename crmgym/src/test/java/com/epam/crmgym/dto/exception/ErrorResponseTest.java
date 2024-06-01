package com.epam.crmgym.dto.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorResponseTest {

    @Test
    public void testConstructorWithMessage() {
        String message = "Test message";
        ErrorResponse errorResponse = new ErrorResponse(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    public void testSetterAndGetter() {
        String message = "Test message";
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        ErrorResponse errorResponse1 = new ErrorResponse("Test message");
        ErrorResponse errorResponse2 = new ErrorResponse("Test message");
        ErrorResponse errorResponse3 = new ErrorResponse("Different message");

        assertEquals(errorResponse1, errorResponse2);
        assertEquals(errorResponse1.hashCode(), errorResponse2.hashCode());
        assertNotEquals(errorResponse1, errorResponse3);
    }

    @Test
    public void testToString() {
        String message = "Test message";
        ErrorResponse errorResponse = new ErrorResponse(message);
        assertEquals("ErrorResponse(message=Test message)", errorResponse.toString());
    }
}
