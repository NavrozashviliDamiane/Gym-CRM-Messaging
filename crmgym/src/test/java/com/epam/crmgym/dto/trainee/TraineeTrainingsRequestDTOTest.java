package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeTrainingsRequestDTO;
import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.ValidTrainingType;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TraineeTrainingsRequestDTOTest {

    private Validator validator;

    @Mock
    private DateDeSerializer dateDeSerializer;

    @Mock
    private ValidTrainingType validTrainingType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TraineeTrainingsRequestDTO requestDTO = new TraineeTrainingsRequestDTO("testUsername", new Date(), new Date(), "trainerName", "trainingTypeName");

        Set<ConstraintViolation<TraineeTrainingsRequestDTO>> violations = validator.validate(requestDTO);
        assertEquals(1, violations.size());
    }

    @Test
    public void testValidationFailure() {
        TraineeTrainingsRequestDTO requestDTO = new TraineeTrainingsRequestDTO(null, null, null, null, null);

        Set<ConstraintViolation<TraineeTrainingsRequestDTO>> violations = validator.validate(requestDTO);
        assertEquals(2, violations.size());
    }


}
