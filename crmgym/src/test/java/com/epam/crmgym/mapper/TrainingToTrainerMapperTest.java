package com.epam.crmgym.mapper;

import static org.junit.jupiter.api.Assertions.*;


import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainingToTrainerMapperTest {

    @Test
    void testMapTrainingToTrainerDTO() {
        TrainingToTrainerMapper mapper = new TrainingToTrainerMapper();

        Training training = new Training();
        training.setTrainingName("Test Training");

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.CARDIO);
        training.setTrainingType(trainingType);

        Trainer trainer = new Trainer();
        User trainerUser = new User();
        trainerUser.setUsername("trainer_username");
        trainerUser.setFirstName("John");
        trainerUser.setLastName("Doe");
        trainer.setUser(trainerUser);
        training.setTrainer(trainer);

        TrainerDTO trainerDTO = mapper.mapTrainingToTrainerDTO(training);

        assertEquals("trainer_username", trainerDTO.getUsername());
        assertEquals("John", trainerDTO.getFirstName());
        assertEquals("Doe", trainerDTO.getLastName());
        assertEquals(TrainingTypeValue.CARDIO.toString(), trainerDTO.getSpecialization());

    }
}
