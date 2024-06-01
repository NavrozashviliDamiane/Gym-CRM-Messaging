package com.epam.crmgym.dto.training;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingTypeDTOTest {

    @Test
    public void testConstructorAndGetters() {
        String trainingType = "Type";
        Long trainingTypeId = 1L;

        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO(trainingType, trainingTypeId);

        assertEquals(trainingType, trainingTypeDTO.getTrainingType());
        assertEquals(trainingTypeId, trainingTypeDTO.getTrainingTypeId());
    }

    @Test
    public void testSetterAndGetters() {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();

        String trainingType = "Type";
        Long trainingTypeId = 1L;

        trainingTypeDTO.setTrainingType(trainingType);
        trainingTypeDTO.setTrainingTypeId(trainingTypeId);

        assertEquals(trainingType, trainingTypeDTO.getTrainingType());
        assertEquals(trainingTypeId, trainingTypeDTO.getTrainingTypeId());
    }
}
