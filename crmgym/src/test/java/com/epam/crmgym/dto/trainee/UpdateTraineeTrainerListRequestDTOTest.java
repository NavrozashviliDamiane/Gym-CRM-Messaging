package com.epam.crmgym.dto.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.UpdateTraineeTrainerListRequestDTO;
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

public class UpdateTraineeTrainerListRequestDTOTest {

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

        UpdateTraineeTrainerListRequestDTO requestDTO = new UpdateTraineeTrainerListRequestDTO("testUsername", trainerUsernames);

        Set<ConstraintViolation<UpdateTraineeTrainerListRequestDTO>> violations = validator.validate(requestDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testValidationFailure() {
        // Test when username is null
        UpdateTraineeTrainerListRequestDTO requestDTO1 = new UpdateTraineeTrainerListRequestDTO(null, new ArrayList<>());
        Set<ConstraintViolation<UpdateTraineeTrainerListRequestDTO>> violations1 = validator.validate(requestDTO1);
        assertEquals(3, violations1.size());

        // Test when trainerUsernames list is empty
        UpdateTraineeTrainerListRequestDTO requestDTO2 = new UpdateTraineeTrainerListRequestDTO("testUsername", new ArrayList<>());
        Set<ConstraintViolation<UpdateTraineeTrainerListRequestDTO>> violations2 = validator.validate(requestDTO2);
        assertEquals(1, violations2.size());
    }
}
