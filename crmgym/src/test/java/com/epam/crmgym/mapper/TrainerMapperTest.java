package com.epam.crmgym.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.entity.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerMapperTest {

    @Test
    public void testConvertToTrainerDTO_ValidInput() {
        TrainerMapper trainerMapper = new TrainerMapper();

        User user = new User();
        user.setUsername("john.doe");
        user.setFirstName("John");
        user.setLastName("Doe");

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.CARDIO);

        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        TrainerDTO trainerDTO = trainerMapper.convertToTrainerDTO(trainer);

        assertEquals("john.doe", trainerDTO.getUsername());
        assertEquals("John", trainerDTO.getFirstName());
        assertEquals("Doe", trainerDTO.getLastName());
        assertEquals("CARDIO", trainerDTO.getSpecialization());
    }



}
