package com.epam.trainerworkload.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleValidationException_shouldReturnBadRequest() {
        FieldError trainerUsernameError = new FieldError("objectName", "trainerUsername", "Trainer username cannot be blank");
        FieldError firstNameError = new FieldError("objectName", "firstName", "First name cannot be blank");

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(trainerUsernameError, firstNameError));

        ResponseEntity<Object> responseEntity = globalExceptionHandler.handleValidationException(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<String, String> body = (Map<String, String>) responseEntity.getBody();
        assertEquals("Trainer username cannot be blank", body.get("trainerUsername"));
        assertEquals("First name cannot be blank", body.get("firstName"));
    }
}
