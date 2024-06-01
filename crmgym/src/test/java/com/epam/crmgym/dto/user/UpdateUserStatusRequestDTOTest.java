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

public class UpdateUserStatusRequestDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        UpdateUserStatusRequestDTO request = new UpdateUserStatusRequestDTO("username", true);

        Set<ConstraintViolation<UpdateUserStatusRequestDTO>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        UpdateUserStatusRequestDTO request1 = new UpdateUserStatusRequestDTO(null, true);
        Set<ConstraintViolation<UpdateUserStatusRequestDTO>> violations1 = validator.validate(request1);
        assertEquals(2, violations1.size());

        UpdateUserStatusRequestDTO request2 = new UpdateUserStatusRequestDTO("username", null);
        Set<ConstraintViolation<UpdateUserStatusRequestDTO>> violations2 = validator.validate(request2);
        assertEquals(2, violations2.size());
    }


}
