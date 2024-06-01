package com.epam.crmgym.util.trainee;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.training.TrainingDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetTraineeTrainingsHelperTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TrainingRepository trainingRepository;

    private GetTraineeTrainingsHelper trainingsHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingsHelper = new GetTraineeTrainingsHelper(trainerRepository, trainingTypeRepository, trainingRepository);
    }

    @Test
    void testGetTrainerId_WhenTrainerExists() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        when(trainerRepository.findByUserUsername(anyString())).thenReturn(trainer);

        Long trainerId = trainingsHelper.getTrainerId("trainer_username");

        assertNotNull(trainerId);
        assertEquals(1L, trainerId);
    }

    @Test
    void testGetTrainerId_WhenTrainerDoesNotExist() {
        when(trainerRepository.findByUserUsername(anyString())).thenReturn(null);

        Long trainerId = trainingsHelper.getTrainerId("non_existing_trainer_username");

        assertNull(trainerId);
    }

    @Test
    void testGetTrainingTypeId_WhenTrainingTypeExists() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        when(trainingTypeRepository.findByTrainingType(TrainingTypeValue.WEIGHT_TRAINING)).thenReturn(trainingType);

        Long trainingTypeId = trainingsHelper.getTrainingTypeId("WEIGHT_TRAINING");

        assertNotNull(trainingTypeId);
        assertEquals(1L, trainingTypeId);
    }



    @Test
    void testConstructQuerys() {
        List<Training> trainings = new ArrayList<>();
        when(trainingRepository.findByTraineeId(1L)).thenReturn(trainings);

        List<Training> result = trainingsHelper.constructQuery(1L, new Date(), new Date(), 1L, 1L);

        assertNotNull(result);
        assertEquals(trainings, result);
    }

    @Test
    void testMapToTrainingDTOS() {
        List<Training> trainings = new ArrayList<>();
        Training training = new Training();
        training.setTrainingName("Test Training");
        training.setTrainingDate(new Date());
        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.WEIGHT_TRAINING);
        training.setTrainingType(trainingType);
        training.setTrainingDuration(60);
        trainings.add(training);

        List<TrainingDTO> trainingDTOList = trainingsHelper.mapToTrainingDTO(trainings);

        assertNotNull(trainingDTOList);
        assertFalse(trainingDTOList.isEmpty());

        TrainingDTO trainingDTO = trainingDTOList.get(0);
        assertEquals("Test Training", trainingDTO.getTrainingName());
        assertEquals(training.getTrainingDate(), trainingDTO.getTrainingDate());
        assertEquals(TrainingTypeValue.WEIGHT_TRAINING.toString(), trainingDTO.getTrainingType());
        assertEquals(60, trainingDTO.getTrainingDuration());
        assertEquals("Unknown", trainingDTO.getTrainerName());
    }

    @Test
    public void testGetTrainerId() {
        String trainerName = "TestTrainer";
        Long expectedTrainerId = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(expectedTrainerId);
        when(trainerRepository.findByUserUsername(trainerName)).thenReturn(trainer);

        Long actualTrainerId = trainingsHelper.getTrainerId(trainerName);

        assertEquals(expectedTrainerId, actualTrainerId);
    }



    @Test
    public void testConstructQuery() {
        Long traineeId = 1L;
        Date fromDate = new Date();
        Date toDate = new Date();
        Long trainerId = 1L;
        Long trainingTypeId = 1L;

        List<Training> expectedTrainings = new ArrayList<>();
        // Add some dummy training objects to the list

        when(trainingRepository.findTrByTnIdAndDtBtwnAndTrIdAndTyIdAndDurGt(traineeId, fromDate, toDate, trainerId, trainingTypeId, 0)).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingsHelper.constructQuery(traineeId, fromDate, toDate, trainerId, trainingTypeId);

        assertEquals(expectedTrainings, actualTrainings);
    }

    @Test
    public void testMapToTrainingDTO() {
        List<Training> trainings = new ArrayList<>();
        // Add some dummy training objects to the list

        List<TrainingDTO> expectedTrainingDTOs = trainings.stream()
                .map(training -> {
                    TrainingDTO trainingDTO = new TrainingDTO();
                    trainingDTO.setTrainingName(training.getTrainingName());
                    trainingDTO.setTrainingDate(training.getTrainingDate());
                    if (training.getTrainingType() != null) {
                        trainingDTO.setTrainingType(training.getTrainingType().getTrainingType().toString());
                    } else {
                        trainingDTO.setTrainingType("Unknown");
                    }
                    trainingDTO.setTrainingDuration(training.getTrainingDuration());
                    if (training.getTrainer() != null && training.getTrainer().getUser() != null) {
                        trainingDTO.setTrainerName(training.getTrainer().getUser().getFirstName() + " " + training.getTrainer().getUser().getLastName());
                    } else {
                        trainingDTO.setTrainerName("Unknown");
                    }
                    return trainingDTO;
                })
                .collect(Collectors.toList());

        List<TrainingDTO> actualTrainingDTOs = trainingsHelper.mapToTrainingDTO(trainings);

        assertEquals(expectedTrainingDTOs, actualTrainingDTOs);
    }
}
