package com.epam.crmgym.exception;

import com.epam.crmgym.exception.ControllerExceptionHandler.ErrorResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerExceptionHandlerTest {

    @Test
    public void testHandleIllegalArgumentException() {
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");

        ResponseEntity<ErrorResponseDTO> response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal argument", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }



    @Test
    public void testHandleNoSuchElementException() {
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        NoSuchElementException ex = new NoSuchElementException("Not found");

        ResponseEntity<ErrorResponseDTO> response = handler.handleNoSuchElementException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    public void testHandleDataIntegrityViolationException() {
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Conflict");

        ResponseEntity<ErrorResponseDTO> response = handler.handleDataIntegrityViolationException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Conflict", response.getBody().getMessage());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
    }

    @Test
    public void testHandleException() {
        ControllerExceptionHandler handler = new ControllerExceptionHandler();
        Exception ex = new Exception("Internal server error");

        ResponseEntity<ErrorResponseDTO> response = handler.handleException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }
}
