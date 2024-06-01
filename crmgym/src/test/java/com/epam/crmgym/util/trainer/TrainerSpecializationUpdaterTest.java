package com.epam.crmgym.util.trainer;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.epam.crmgym.dto.trainer.TrainerUpdateDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TrainerSpecializationUpdaterTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainerSpecializationUpdater specializationUpdater;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateSpecialization_WithValidSpecialization() {
        // Arrange
        Trainer trainer = new Trainer();
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();
        updateDTO.setSpecialization("cardio");
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.CARDIO);
        when(trainingTypeRepository.findByTrainingType(TrainingTypeValue.CARDIO))
                .thenReturn(trainingType);

        // Act
        specializationUpdater.updateSpecialization(trainer, updateDTO);

        // Assert
        assertEquals(trainingType, trainer.getTrainingType());
    }

    @Test
    public void testUpdateSpecialization_WithNullSpecialization() {
        // Arrange
        Trainer trainer = new Trainer();
        TrainerUpdateDTO updateDTO = new TrainerUpdateDTO();

        // Act
        specializationUpdater.updateSpecialization(trainer, updateDTO);

        // Assert
        assertEquals(null, trainer.getTrainingType());
        verify(trainingTypeRepository, never()).findByTrainingType(any());
    }


}
