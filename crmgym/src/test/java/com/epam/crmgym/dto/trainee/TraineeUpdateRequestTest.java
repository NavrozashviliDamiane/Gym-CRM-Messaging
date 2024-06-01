package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeUpdateDTO;
import com.epam.crmgym.dto.trainee.TraineeUpdateRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TraineeUpdateRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO("testUsername", "John", "Doe", null, "123 Main St", true);
        TraineeUpdateRequest updateRequest = new TraineeUpdateRequest("testUsername", "password", updateDTO);

        Set<ConstraintViolation<TraineeUpdateRequest>> violations = validator.validate(updateRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testValidationFailure() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO("testUsername", "John", "Doe", null, "123 Main St", true);
        TraineeUpdateRequest updateRequest = new TraineeUpdateRequest(null, null, updateDTO);

        Set<ConstraintViolation<TraineeUpdateRequest>> violations = validator.validate(updateRequest);
        assertEquals(4, violations.size());
    }

    @Test
    public void testNestedDTOValidation() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO("testUsername", "John", "Doe", null, "123 Main St", true);
        TraineeUpdateRequest updateRequest = new TraineeUpdateRequest("testUsername", "password", updateDTO);

        Set<ConstraintViolation<TraineeUpdateRequest>> violations = validator.validate(updateRequest);
        assertTrue(violations.isEmpty());
    }
}
