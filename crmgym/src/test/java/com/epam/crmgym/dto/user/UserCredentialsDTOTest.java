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

public class UserCredentialsDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        UserCredentialsDTO credentialsDTO = new UserCredentialsDTO("username", "password123");

        Set<ConstraintViolation<UserCredentialsDTO>> violations = validator.validate(credentialsDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        UserCredentialsDTO credentialsDTO1 = new UserCredentialsDTO(null, null);
        Set<ConstraintViolation<UserCredentialsDTO>> violations1 = validator.validate(credentialsDTO1);
        assertEquals(4, violations1.size());

        UserCredentialsDTO credentialsDTO2 = new UserCredentialsDTO("", "password123");
        Set<ConstraintViolation<UserCredentialsDTO>> violations2 = validator.validate(credentialsDTO2);
        assertEquals(1, violations2.size());

        UserCredentialsDTO credentialsDTO3 = new UserCredentialsDTO("username", "");
        Set<ConstraintViolation<UserCredentialsDTO>> violations3 = validator.validate(credentialsDTO3);
        assertEquals(1, violations3.size());
    }
}
