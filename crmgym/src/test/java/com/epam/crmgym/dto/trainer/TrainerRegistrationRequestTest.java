package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerRegistrationRequest;
import com.epam.crmgym.validation.ValidTrainingType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainerRegistrationRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    @Test
    public void testValidationFailure() {
        // Test when firstName and lastName are null
        TrainerRegistrationRequest request1 = new TrainerRegistrationRequest(null, null, "Java");
        Set<ConstraintViolation<TrainerRegistrationRequest>> violations1 = validator.validate(request1);
        assertEquals(5, violations1.size());

        // Test when specialization is null
        TrainerRegistrationRequest request2 = new TrainerRegistrationRequest("John", "Doe", null);
        Set<ConstraintViolation<TrainerRegistrationRequest>> violations2 = validator.validate(request2);
        assertEquals(2, violations2.size());
    }


}
