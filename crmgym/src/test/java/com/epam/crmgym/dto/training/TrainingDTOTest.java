package com.epam.crmgym.dto.training;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.training.TrainingDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String trainingName = "Training";
        Date trainingDate = new Date();
        String trainingType = "Type";
        Integer trainingDuration = 60;
        String trainerName = "Trainer";

        TrainingDTO trainingDTO = new TrainingDTO(trainingName, trainingDate, trainingType, trainingDuration, trainerName);

        assertEquals(trainingName, trainingDTO.getTrainingName());
        assertEquals(trainingDate, trainingDTO.getTrainingDate());
        assertEquals(trainingType, trainingDTO.getTrainingType());
        assertEquals(trainingDuration, trainingDTO.getTrainingDuration());
        assertEquals(trainerName, trainingDTO.getTrainerName());
    }

    @Test
    public void testSetterAndGetters() {
        TrainingDTO trainingDTO = new TrainingDTO();

        String trainingName = "Training";
        Date trainingDate = new Date();
        String trainingType = "Type";
        Integer trainingDuration = 60;
        String trainerName = "Trainer";

        trainingDTO.setTrainingName(trainingName);
        trainingDTO.setTrainingDate(trainingDate);
        trainingDTO.setTrainingType(trainingType);
        trainingDTO.setTrainingDuration(trainingDuration);
        trainingDTO.setTrainerName(trainerName);

        assertEquals(trainingName, trainingDTO.getTrainingName());
        assertEquals(trainingDate, trainingDTO.getTrainingDate());
        assertEquals(trainingType, trainingDTO.getTrainingType());
        assertEquals(trainingDuration, trainingDTO.getTrainingDuration());
        assertEquals(trainerName, trainingDTO.getTrainerName());
    }

    @Test
    public void testJsonFormatAnnotation() throws NoSuchFieldException {
        // Assuming the date format is "yyyy-MM-dd" and timezone is "UTC"
        JsonFormat jsonFormatAnnotation = TrainingDTO.class.getDeclaredField("trainingDate").getAnnotation(JsonFormat.class);

        assertEquals("yyyy-MM-dd", jsonFormatAnnotation.pattern());
        assertEquals("UTC", jsonFormatAnnotation.timezone());
    }
}
