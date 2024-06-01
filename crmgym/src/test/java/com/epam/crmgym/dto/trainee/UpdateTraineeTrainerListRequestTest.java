package com.epam.crmgym.dto.trainee;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateTraineeTrainerListRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("trainer1");
        trainerUsernames.add("trainer2");

        UpdateTraineeTrainerListRequest request = new UpdateTraineeTrainerListRequest("testUsername", "password", trainerUsernames);

        Set<ConstraintViolation<UpdateTraineeTrainerListRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        UpdateTraineeTrainerListRequest request1 = new UpdateTraineeTrainerListRequest(null, null, new ArrayList<>());
        Set<ConstraintViolation<UpdateTraineeTrainerListRequest>> violations1 = validator.validate(request1);
        assertEquals(0, violations1.size());

        UpdateTraineeTrainerListRequest request2 = new UpdateTraineeTrainerListRequest("testUsername", "password", new ArrayList<>());
        Set<ConstraintViolation<UpdateTraineeTrainerListRequest>> violations2 = validator.validate(request2);
        assertEquals(0, violations2.size());
    }
}
