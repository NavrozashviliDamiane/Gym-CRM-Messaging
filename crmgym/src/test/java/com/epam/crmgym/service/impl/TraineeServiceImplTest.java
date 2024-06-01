package com.epam.crmgym.service.impl;

import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.dto.trainer.TrainerResponse;
import com.epam.crmgym.dto.training.TrainingDTO;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.mapper.TrainingToTrainerMapper;
import com.epam.crmgym.repository.TraineeRepository;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.service.TrainingService;
import com.epam.crmgym.service.UserService;
import com.epam.crmgym.util.trainee.GetTraineeTrainingsHelper;
import com.epam.crmgym.util.trainee.UpdateTraineeTrainersListHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
public class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private GetTraineeTrainingsHelper getTraineeTrainingsHelper;

    @Mock
    private UpdateTraineeTrainersListHelper updateTraineeTrainersListHelper;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @Mock
    private TrainingToTrainerMapper trainingToTrainerMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTraineeProfile() {
        String username = "testTrainee";
        Trainee trainee = new Trainee();
        trainee.setId(1L); // Assuming ID is set
        trainee.setDateOfBirth(new Date());
        trainee.setAddress("Test Address");
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Trainee");
        user.setActive(true);
        trainee.setUser(user);

        List<Training> trainings = new ArrayList<>();
        Training training1 = new Training();
        training1.setId(1L);
        Training training2 = new Training();
        training2.setId(2L);
        trainings.add(training1);
        trainings.add(training2);

        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();
        trainers.add(trainer1);
        trainers.add(trainer2);

        when(traineeRepository.findByUserUsername(username)).thenReturn(trainee);
        when(trainingRepository.findByTraineeId(trainee.getId())).thenReturn(trainings);
        when(trainingToTrainerMapper.mapTrainingToTrainerDTO(any())).thenReturn(new TrainerDTO());

        TraineeProfileDTO profileDTO = traineeService.getTraineeProfile(username);

        verify(traineeRepository).findByUserUsername(username);
        verify(trainingRepository).findByTraineeId(trainee.getId());
        verify(trainingToTrainerMapper, times(2)).mapTrainingToTrainerDTO(any());

        assertEquals(user.getFirstName(), profileDTO.getFirstName());
        assertEquals(user.getLastName(), profileDTO.getLastName());
        assertEquals(trainee.getDateOfBirth(), profileDTO.getDateOfBirth());
        assertEquals(trainee.getAddress(), profileDTO.getAddress());
        assertEquals(user.isActive(), profileDTO.isActive());
        assertEquals(trainers.size(), profileDTO.getTrainers().size());
    }



    @Test
    @Transactional
    public void testUpdateTraineeProfile_NotFound() {
        String username = "nonExistingTrainee";

        when(traineeRepository.findByUserUsername(username)).thenReturn(null);

        Trainee updatedTrainee = traineeService.updateTraineeProfile(username, "John", "Doe", new Date(), "Address", true);

        verify(traineeRepository).findByUserUsername(username);
        verify(userService, never()).saveUser(any());

        assertNull(updatedTrainee);
    }

    @Test
    public void testCreateTrainee() throws UsernameValidationException {
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Main St";

        User user = new User();
        when(userService.createUser(firstName, lastName)).thenReturn(user);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);

        when(traineeRepository.save(trainee)).thenReturn(trainee);

        Trainee createdTrainee = traineeService.createTrainee(firstName, lastName, dateOfBirth, address);

        verify(userService).createUser(firstName, lastName);

        verify(traineeRepository).save(trainee);

        assertEquals(trainee, createdTrainee);
    }



    @Test
    @Transactional
    void testUpdateTraineeStatus() {
        String username = "testTrainee";
        boolean isActive = true;

        Trainee mockedTrainee = new Trainee();
        User mockedUser = new User();
        mockedUser.setActive(false);
        mockedTrainee.setUser(mockedUser);

        when(traineeRepository.findByUserUsername(username)).thenReturn(mockedTrainee);

        assertDoesNotThrow(() -> traineeService.updateTraineeStatus(username, isActive));

        verify(traineeRepository).findByUserUsername(username);
        verify(userService).saveUser(any(User.class));
    }


    @Test
    @Transactional
    void testDeleteTraineeByUsername() {
        String username = "testTrainee";

        Trainee mockedTrainee = new Trainee();
        mockedTrainee.setId(1L);
        User mockedUser = new User();
        mockedUser.setId(1L);
        mockedTrainee.setUser(mockedUser);
        List<Training> mockedTrainings = Arrays.asList(new Training(), new Training());
        when(traineeRepository.findByUserUsername(username)).thenReturn(mockedTrainee);
        when(trainingRepository.findByTraineeId(mockedTrainee.getId())).thenReturn(mockedTrainings);

        assertDoesNotThrow(() -> traineeService.deleteTraineeByUsername(username));

        verify(traineeRepository).findByUserUsername(username);
        verify(trainingRepository).findByTraineeId(mockedTrainee.getId());
        verify(trainingRepository).deleteAll(mockedTrainings);
        verify(userService).deleteUserById(mockedUser.getId());
        verify(traineeRepository).delete(mockedTrainee);
    }


    @Test
    void testGetTraineeTrainingsList() {
        String username = "testTrainee";
        Date fromDate = new Date();
        Date toDate = new Date();
        String trainerName = "Trainer Name";
        String trainingTypeName = "Training Type Name";

        Trainee mockedTrainee = new Trainee();
        mockedTrainee.setId(1L);
        when(traineeRepository.findByUserUsername(username)).thenReturn(mockedTrainee);

        Long trainerId = 1L;
        Long trainingTypeId = 2L;

        List<Training> mockedTrainings = Arrays.asList(new Training(), new Training());
        when(getTraineeTrainingsHelper.getTrainerId(trainerName)).thenReturn(trainerId);
        when(getTraineeTrainingsHelper.getTrainingTypeId(trainingTypeName)).thenReturn(trainingTypeId);
        when(getTraineeTrainingsHelper.constructQuery(anyLong(), any(Date.class), any(Date.class), anyLong(), anyLong())).thenReturn(mockedTrainings);
        when(getTraineeTrainingsHelper.mapToTrainingDTO(mockedTrainings)).thenReturn(Arrays.asList(new TrainingDTO(), new TrainingDTO())); // Mocking two TrainingDTOs

        List<TrainingDTO> result = traineeService.getTraineeTrainingsList(username, fromDate, toDate, trainerName, trainingTypeName);

        assertEquals(2, result.size());
        for (TrainingDTO trainingDTO : result) {
            assertNotNull(trainingDTO);
        }

        verify(traineeRepository).findByUserUsername(username);
        verify(getTraineeTrainingsHelper).getTrainerId(trainerName);
        verify(getTraineeTrainingsHelper).getTrainingTypeId(trainingTypeName);
        verify(getTraineeTrainingsHelper).constructQuery(mockedTrainee.getId(), fromDate, DateUtils.addDays(toDate, 1), trainerId, trainingTypeId);
        verify(getTraineeTrainingsHelper).mapToTrainingDTO(mockedTrainings);
    }


    @Test
    public void testUpdateTraineeProfile() {
        // Mock data
        String username = "testUser";
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        Boolean isActive = true;

        Trainee trainee = new Trainee();
        trainee.setUser(new User());

        when(traineeRepository.findByUserUsername(username)).thenReturn(trainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(null);

        Trainee updatedTrainee = traineeService.updateTraineeProfile(username, firstName, lastName, dateOfBirth, address, isActive);

        verify(traineeRepository).findByUserUsername(username);
        verify(traineeRepository).save(any(Trainee.class));
        verify(userService).saveUser(any(User.class));

        assertNull(updatedTrainee);
    }



    @Test
    @Transactional
    public void testUpdateTraineeProfile_UserNotFound() {
        String username = "nonExistingUser";
        String firstName = "John";
        String lastName = "Doe";
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        Boolean isActive = true;

        when(traineeRepository.findByUserUsername(username)).thenReturn(null);

        Trainee updatedTrainee = traineeService.updateTraineeProfile(username, firstName, lastName, dateOfBirth, address, isActive);

        verify(traineeRepository).findByUserUsername(username);

        verifyNoInteractions(userService);

        assertEquals(null, updatedTrainee);
    }




}
