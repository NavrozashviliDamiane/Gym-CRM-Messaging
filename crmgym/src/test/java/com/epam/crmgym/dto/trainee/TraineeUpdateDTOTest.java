package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeUpdateDTO;
import com.epam.crmgym.exception.DateDeSerializer;
import com.epam.crmgym.validation.RequiredBoolean;
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

public class TraineeUpdateDTOTest {

    private Validator validator;

    @Mock
    private DateDeSerializer dateDeSerializer;

    @Mock
    private RequiredBoolean requiredBoolean;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidationSuccess() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO("testUsername", "John", "Doe", new Date(), "123 Main St", true);

        Set<ConstraintViolation<TraineeUpdateDTO>> violations = validator.validate(updateDTO);
        assertEquals(0, violations.size());
    }

    @Test
    public void testValidationFailure() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO(null, null, null, null, null, null);

        Set<ConstraintViolation<TraineeUpdateDTO>> violations = validator.validate(updateDTO);
        assertEquals(8, violations.size());
    }


}
