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

public class ChangePasswordRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        ChangePasswordRequest request = new ChangePasswordRequest("username", "oldPassword", "newPassword123");

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        ChangePasswordRequest request1 = new ChangePasswordRequest(null, null, null);
        Set<ConstraintViolation<ChangePasswordRequest>> violations1 = validator.validate(request1);
        assertEquals(6, violations1.size());

        ChangePasswordRequest request2 = new ChangePasswordRequest("username", "oldPassword", "weak");
        Set<ConstraintViolation<ChangePasswordRequest>> violations2 = validator.validate(request2);
        assertEquals(1, violations2.size());
    }


}
