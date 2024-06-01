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

public class UsernameDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        UsernameDTO usernameDTO = new UsernameDTO("username");

        Set<ConstraintViolation<UsernameDTO>> violations = validator.validate(usernameDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        UsernameDTO usernameDTO1 = new UsernameDTO(null);
        Set<ConstraintViolation<UsernameDTO>> violations1 = validator.validate(usernameDTO1);
        assertEquals(2, violations1.size());

        UsernameDTO usernameDTO2 = new UsernameDTO("");
        Set<ConstraintViolation<UsernameDTO>> violations2 = validator.validate(usernameDTO2);
        assertEquals(1, violations2.size());
    }
}
