package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerUpdateDTO;
import com.epam.crmgym.validation.RequiredBoolean;
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

public class TrainerUpdateDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }



    @Test
    public void testValidationFailure() {
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO(null, null, null, true, null);
        Set<ConstraintViolation<TrainerUpdateDTO>> violations = validator.validate(updateDTO);
        assertEquals(8, violations.size());

        TrainerUpdateDTO updateDTO2 = new TrainerUpdateDTO("username", "John", "Doe", null, "Java");
        Set<ConstraintViolation<TrainerUpdateDTO>> violations2 = validator.validate(updateDTO2);
        assertEquals(3, violations2.size());

        TrainerUpdateDTO updateDTO3 = new TrainerUpdateDTO("username", "John", "Doe", false, null);
        Set<ConstraintViolation<TrainerUpdateDTO>> violations3 = validator.validate(updateDTO3);
        assertEquals(2, violations3.size());
    }


}
