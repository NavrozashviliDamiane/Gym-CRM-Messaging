package com.epam.crmgym.util.trainee;

import com.epam.crmgym.dto.trainer.TrainerResponse;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.entity.User; // Make sure to import the User class
import com.epam.crmgym.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateTraineeTrainersListHelperTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private UpdateTraineeTrainersListHelper updateTraineeTrainersListHelper;

    @Test
    public void testGetUpdatedTrainers_EmptyList() {
        List<Training> trainings = new ArrayList<>();

        List<TrainerResponse> updatedTrainers = updateTraineeTrainersListHelper.getUpdatedTrainers(trainings, trainerRepository);

        assertNotNull(updatedTrainers);
        assertTrue(updatedTrainers.isEmpty());
    }

    @Test
    public void testGetUpdatedTrainers_WithTrainings() throws Exception {
        Training training1 = new Training();
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        User user = new User();
        user.setUsername("trainer1_username");
        user.setFirstName("Trainer1");
        user.setLastName("Last");
        trainer1.setUser(user);
        training1.setTrainer(trainer1);

        TrainingType trainingType = new TrainingType(TrainingTypeValue.CARDIO);
        training1.setTrainingType(trainingType);

        Training training2 = new Training();
        training2.setTrainer(null);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training1);
        trainings.add(training2);

        when(trainerRepository.findById(trainer1.getId())).thenReturn(Optional.of(trainer1));

        List<TrainerResponse> updatedTrainers = updateTraineeTrainersListHelper.getUpdatedTrainers(trainings, trainerRepository);

        assertNotNull(updatedTrainers);
        assertEquals(1, updatedTrainers.size());

        TrainerResponse trainerResponse = updatedTrainers.get(0);
        assertEquals("trainer1_username", trainerResponse.getUsername());
        assertEquals("Trainer1", trainerResponse.getFirstName());
        assertEquals("Last", trainerResponse.getLastName());
        assertEquals("CARDIO", trainerResponse.getSpecialization());
    }

}

