package com.epam.crmgym.mapper;

import com.epam.crmgym.dto.trainer.TrainerTrainingResponseDTO;
import com.epam.crmgym.entity.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TrainerTrainingMapperTest {

    @Test
    void testMapTrainingToResponseDTO() {
        // Arrange
        TrainerTrainingMapper mapper = new TrainerTrainingMapper();
        Training training = new Training();
        training.setTrainingName("Test Training");
        training.setTrainingDate(new Date());
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.WEIGHT_TRAINING);
        training.setTrainingType(trainingType);
        training.setTrainingDuration(60);

        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername("trainee_username");
        trainee.setUser(user);
        training.setTrainee(trainee);

        // Act
        TrainerTrainingResponseDTO responseDTO = mapper.mapTrainingToResponseDTO(training);

        // Assert
        assertEquals("Test Training", responseDTO.getTrainingName());
        assertEquals(training.getTrainingDate(), responseDTO.getTrainingDate());
        // Assert
        assertEquals("WEIGHT_TRAINING", responseDTO.getTrainingType().toString());

        assertEquals(60, responseDTO.getTrainingDuration());
        assertEquals("trainee_username", responseDTO.getTraineeName());
    }
}
