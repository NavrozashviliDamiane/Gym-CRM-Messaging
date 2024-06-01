package com.epam.crmgym.dto.training;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.training.TrainingRequest;
import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.ValidTrainingDuration;
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

public class TrainingRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TrainingRequest request = new TrainingRequest("traineeUsername", "trainerUsername", "Training", new Date(System.currentTimeMillis() + 1000), 60);

        Set<ConstraintViolation<TrainingRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        TrainingRequest request1 = new TrainingRequest(null, null, null, null, null);
        Set<ConstraintViolation<TrainingRequest>> violations1 = validator.validate(request1);
        assertEquals(9, violations1.size());

        Date pastDate = new Date(System.currentTimeMillis() - 1000);
        TrainingRequest request2 = new TrainingRequest("traineeUsername", "trainerUsername", "Training", pastDate, 60);
        Set<ConstraintViolation<TrainingRequest>> violations2 = validator.validate(request2);
        assertEquals(1, violations2.size());

        TrainingRequest request3 = new TrainingRequest("traineeUsername", "trainerUsername", "Training", new Date(System.currentTimeMillis() + 1000), 0);
        Set<ConstraintViolation<TrainingRequest>> violations3 = validator.validate(request3);
        assertEquals(1, violations3.size());
    }


}
