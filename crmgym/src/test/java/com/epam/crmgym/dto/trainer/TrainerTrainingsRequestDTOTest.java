package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerTrainingsRequestDTO;
import com.epam.crmgym.exception.DateDeSerializer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainerTrainingsRequestDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TrainerTrainingsRequestDTO request = new TrainerTrainingsRequestDTO("username", new Date(), new Date(), "traineeName", "password");

        Set<ConstraintViolation<TrainerTrainingsRequestDTO>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        // Test when username and password are null
        TrainerTrainingsRequestDTO request1 = new TrainerTrainingsRequestDTO(null, new Date(), new Date(), "traineeName", null);
        Set<ConstraintViolation<TrainerTrainingsRequestDTO>> violations1 = validator.validate(request1);
        assertEquals(4, violations1.size());

        // Test when periodFrom is after periodTo
        Date periodFrom = new Date();
        Date periodTo = new Date(System.currentTimeMillis() - 1000);
        TrainerTrainingsRequestDTO request2 = new TrainerTrainingsRequestDTO("username", periodFrom, periodTo, "traineeName", "password");
        Set<ConstraintViolation<TrainerTrainingsRequestDTO>> violations2 = validator.validate(request2);
        assertEquals(0, violations2.size());
    }


}
