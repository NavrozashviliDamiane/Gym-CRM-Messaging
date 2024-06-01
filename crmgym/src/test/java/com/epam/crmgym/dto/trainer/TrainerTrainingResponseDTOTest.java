package com.epam.crmgym.dto.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainer.TrainerTrainingResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerTrainingResponseDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String trainingName = "Training";
        Date trainingDate = new Date();
        String trainingType = "Type";
        Integer trainingDuration = 60;
        String traineeName = "Trainee";

        TrainerTrainingResponseDTO responseDTO = new TrainerTrainingResponseDTO(trainingName, trainingDate, trainingType, trainingDuration, traineeName);

        assertEquals(trainingName, responseDTO.getTrainingName());
        assertEquals(trainingDate, responseDTO.getTrainingDate());
        assertEquals(trainingType, responseDTO.getTrainingType());
        assertEquals(trainingDuration, responseDTO.getTrainingDuration());
        assertEquals(traineeName, responseDTO.getTraineeName());
    }

    @Test
    public void testSetterAndGetters() {
        TrainerTrainingResponseDTO responseDTO = new TrainerTrainingResponseDTO();

        String trainingName = "Training";
        Date trainingDate = new Date();
        String trainingType = "Type";
        Integer trainingDuration = 60;
        String traineeName = "Trainee";

        responseDTO.setTrainingName(trainingName);
        responseDTO.setTrainingDate(trainingDate);
        responseDTO.setTrainingType(trainingType);
        responseDTO.setTrainingDuration(trainingDuration);
        responseDTO.setTraineeName(traineeName);

        assertEquals(trainingName, responseDTO.getTrainingName());
        assertEquals(trainingDate, responseDTO.getTrainingDate());
        assertEquals(trainingType, responseDTO.getTrainingType());
        assertEquals(trainingDuration, responseDTO.getTrainingDuration());
        assertEquals(traineeName, responseDTO.getTraineeName());
    }
}
