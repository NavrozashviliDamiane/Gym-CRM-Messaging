package com.epam.crmgym.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.repository.TrainingRepository;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;

public class TraineeMapperTest {

    @Test
    public void testMapTraineeToDTO_ValidInput() {
        TrainingRepository trainingRepository = mock(TrainingRepository.class);
        TraineeMapper traineeMapper = new TraineeMapper(trainingRepository);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("123 Main St");

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.CARDIO);

        Trainer trainer = new Trainer();
        User trainerUser = new User();
        trainerUser.setUsername("trainer1");
        trainerUser.setFirstName("Trainer");
        trainerUser.setLastName("One");
        trainer.setUser(trainerUser);
        Training training = new Training();
        training.setTrainingType(trainingType);
        training.setTrainer(trainer);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        when(trainingRepository.findByTraineeId(trainee.getId())).thenReturn(trainings);

        TraineeProfileDTO profileDTO = traineeMapper.mapTraineeToDTO(trainee);

        assertEquals("John", profileDTO.getFirstName());
        assertEquals("Doe", profileDTO.getLastName());
        assertNotNull(profileDTO.getDateOfBirth());
        assertEquals("123 Main St", profileDTO.getAddress());
        assertTrue(profileDTO.isActive());

        List<TrainerDTO> trainers = profileDTO.getTrainers();
        assertEquals(1, trainers.size());

        TrainerDTO trainerDTO = trainers.get(0);
        assertEquals("trainer1", trainerDTO.getUsername());
        assertEquals("Trainer", trainerDTO.getFirstName());
        assertEquals("One", trainerDTO.getLastName());
        assertEquals("CARDIO", trainerDTO.getSpecialization());
    }
}
