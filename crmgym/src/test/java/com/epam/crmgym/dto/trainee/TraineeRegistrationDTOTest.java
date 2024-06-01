package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeRegistrationDTO;
import com.epam.crmgym.exception.DateDeSerializer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TraineeRegistrationDTOTest {

    private Validator validator;

    @Mock
    private DateDeSerializer dateDeSerializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TraineeRegistrationDTO traineeRegistrationDTO = new TraineeRegistrationDTO("John", "Doe", new Date(), "123 Main St");

        Set<ConstraintViolation<TraineeRegistrationDTO>> violations = validator.validate(traineeRegistrationDTO);
        assertEquals(0, violations.size());
    }



}
