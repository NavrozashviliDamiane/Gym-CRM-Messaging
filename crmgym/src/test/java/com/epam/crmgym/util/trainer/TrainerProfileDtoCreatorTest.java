package com.epam.crmgym.util.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.epam.crmgym.dto.trainee.TraineeDTO;
import com.epam.crmgym.dto.trainer.TrainerProfileDTO;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.repository.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainerProfileDtoCreatorTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainerProfileDtoCreator trainerProfileDtoCreator;

    @Test
    public void testCreateTrainerProfileDTO() {
        // Mock data
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUsername("test_username");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setActive(true);
        trainer.setUser(user);

        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingType(TrainingTypeValue.CARDIO);
        trainer.setTrainingType(trainingType);

        Training training1 = new Training();
        Trainee trainee1 = new Trainee();
        User traineeUser1 = new User();
        traineeUser1.setUsername("trainee1_username");
        traineeUser1.setFirstName("Trainee1");
        traineeUser1.setLastName("Last1");
        trainee1.setUser(traineeUser1);
        training1.setTrainee(trainee1);

        Training training2 = new Training();
        Trainee trainee2 = new Trainee();
        User traineeUser2 = new User();
        traineeUser2.setUsername("trainee2_username");
        traineeUser2.setFirstName("Trainee2");
        traineeUser2.setLastName("Last2");
        trainee2.setUser(traineeUser2);
        training2.setTrainee(trainee2);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training1);
        trainings.add(training2);

        // Mock behavior
        when(trainingRepository.findByTrainerId(trainer.getId())).thenReturn(trainings);

        // Call the method
        TrainerProfileDTO profileDTO = trainerProfileDtoCreator.createTrainerProfileDTO(trainer);

        // Assertions
        assertEquals("test_username", profileDTO.getUsername());
        assertEquals("Test", profileDTO.getFirstName());
        assertEquals("User", profileDTO.getLastName());
        assertEquals(true, profileDTO.isActive());
        assertEquals("CARDIO", profileDTO.getSpecialization());

        List<TraineeDTO> traineesDTO = profileDTO.getTrainees();
        assertEquals(2, traineesDTO.size());

        TraineeDTO traineeDTO1 = traineesDTO.get(0);
        assertEquals("trainee1_username", traineeDTO1.getUsername());
        assertEquals("Trainee1", traineeDTO1.getFirstName());
        assertEquals("Last1", traineeDTO1.getLastName());

        TraineeDTO traineeDTO2 = traineesDTO.get(1);
        assertEquals("trainee2_username", traineeDTO2.getUsername());
        assertEquals("Trainee2", traineeDTO2.getFirstName());
        assertEquals("Last2", traineeDTO2.getLastName());
    }
}
