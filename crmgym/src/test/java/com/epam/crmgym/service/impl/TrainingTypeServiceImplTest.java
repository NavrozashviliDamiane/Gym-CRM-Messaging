package com.epam.crmgym.service.impl;


import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @Test
    void createTrainingType_ValidTrainingTypeValue_ReturnsCreatedTrainingType() {
        TrainingTypeValue trainingTypeValue = TrainingTypeValue.CARDIO;
        TrainingType trainingType = new TrainingType(trainingTypeValue);
        when(trainingTypeRepository.save(any())).thenReturn(trainingType);

        TrainingType createdTrainingType = trainingTypeService.createTrainingType(trainingTypeValue);

        assertEquals(trainingType, createdTrainingType);
    }

    @Test
    void getAllTrainingTypes_ReturnsListOfTrainingTypes() {
        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType(TrainingTypeValue.CARDIO));
        trainingTypes.add(new TrainingType(TrainingTypeValue.WEIGHT_TRAINING));
        trainingTypes.add(new TrainingType(TrainingTypeValue.YOGA));
        trainingTypes.add(new TrainingType(TrainingTypeValue.CROSSFIT));
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);

        List<TrainingType> result = trainingTypeService.getAllTrainingTypes();

        assertEquals(trainingTypes.size(), result.size());
        for (int i = 0; i < trainingTypes.size(); i++) {
            assertEquals(trainingTypes.get(i), result.get(i));
        }
    }
}
