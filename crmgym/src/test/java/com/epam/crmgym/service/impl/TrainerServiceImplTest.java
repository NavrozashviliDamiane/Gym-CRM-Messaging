package com.epam.crmgym.service.impl;

import com.epam.crmgym.dto.trainee.TraineeDTO;
import com.epam.crmgym.dto.trainer.TrainerProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerRegistrationRequest;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingTypeRepository;
import com.epam.crmgym.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private UserService userService;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    public void testGetTrainerProfile_TrainerFoundWithTrainings() {
        // Create mock data
        String username = "trainer_username";
        Trainer trainer = new Trainer();
        User user = new User();
        user.setUsername(username);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);
        trainer.setUser(user);
        Training training = new Training();
        Trainee trainee = new Trainee();
        User traineeUser = new User();
        traineeUser.setUsername("trainee_username");
        traineeUser.setFirstName("Alice");
        traineeUser.setLastName("Smith");
        trainee.setUser(traineeUser);
        training.setTrainee(trainee);
        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        when(trainerRepository.findByUserUsername(username)).thenReturn(trainer);
        when(trainingRepository.findByTrainerId(trainer.getId())).thenReturn(trainings);

        TrainerProfileDTO profileDTO = trainerService.getTrainerProfile(username);

        assertNotNull(profileDTO);
        assertEquals(username, profileDTO.getUsername());
        assertEquals("John", profileDTO.getFirstName());
        assertEquals("Doe", profileDTO.getLastName());
        assertTrue(profileDTO.isActive());
        assertNotNull(profileDTO.getTrainees());
        assertEquals(1, profileDTO.getTrainees().size());
        TraineeDTO traineeDTO = profileDTO.getTrainees().get(0);
        assertEquals("trainee_username", traineeDTO.getUsername());
        assertEquals("Alice", traineeDTO.getFirstName());
        assertEquals("Smith", traineeDTO.getLastName());
    }




}
