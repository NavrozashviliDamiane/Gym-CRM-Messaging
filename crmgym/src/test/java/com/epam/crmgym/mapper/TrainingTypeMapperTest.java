package com.epam.crmgym.mapper;

import com.epam.crmgym.dto.training.TrainingTypeDTO;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeMapperTest {

    @Test
    void testMapToTrainingTypeDTO() {
        TrainingTypeMapper mapper = new TrainingTypeMapper();
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingType(TrainingTypeValue.WEIGHT_TRAINING);

        TrainingTypeDTO trainingTypeDTO = mapper.mapToTrainingTypeDTO(trainingType);

        assertNotNull(trainingTypeDTO);
        assertEquals(trainingType.getId(), trainingTypeDTO.getTrainingTypeId());
        assertEquals(TrainingTypeValue.WEIGHT_TRAINING.toString(), trainingTypeDTO.getTrainingType());
    }



}
