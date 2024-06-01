package com.epam.crmgym.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        LoginRequest request = new LoginRequest("username", "password123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        LoginRequest request1 = new LoginRequest(null, null);
        Set<ConstraintViolation<LoginRequest>> violations1 = validator.validate(request1);
        assertEquals(4, violations1.size());

        LoginRequest request2 = new LoginRequest("", "password123");
        Set<ConstraintViolation<LoginRequest>> violations2 = validator.validate(request2);
        assertEquals(1, violations2.size());

        LoginRequest request3 = new LoginRequest("username", "");
        Set<ConstraintViolation<LoginRequest>> violations3 = validator.validate(request3);
        assertEquals(1, violations3.size());
    }
}
