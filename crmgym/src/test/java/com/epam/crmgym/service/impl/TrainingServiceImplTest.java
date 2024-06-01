//package com.epam.crmgym.service.impl;
//
//import com.epam.crmgym.entity.*;
//import com.epam.crmgym.repository.TraineeRepository;
//import com.epam.crmgym.repository.TrainerRepository;
//import com.epam.crmgym.repository.TrainingRepository;
//import com.epam.crmgym.repository.TrainingTypeRepository;
//import com.epam.crmgym.service.AuthenticateService;
//import com.epam.crmgym.service.TrainingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TrainingServiceImplTest {
//
//    @Mock
//    private TrainingRepository trainingRepository;
//
//    @Mock
//    private TraineeRepository traineeRepository;
//
//    @Mock
//    private TrainerRepository trainerRepository;
//
//    @Mock
//    private TrainingTypeRepository trainingTypeRepository;
//
//    @Mock
//    private AuthenticateService authenticateService;
//
//    @InjectMocks
//    private TrainingServiceImpl trainingService;
//
//    @Test
//    void createTraining_ValidCredentials_ReturnCreatedTraining() {
//        String traineeUsername = "trainee";
//        String trainerUsername = "trainer";
//        String trainingName = "Test Training";
//        Date trainingDate = new Date();
//        Integer trainingDuration = 60;
//
//        Trainee trainee = new Trainee();
//        trainee.setId(1L);
//
//        Trainer trainer = new Trainer();
//        trainer.setId(2L);
//
//        Training training = new Training();
//        training.setId(1L);
//        training.setTrainee(trainee);
//        training.setTrainer(trainer);
//        training.setTrainingName(trainingName);
//        training.setTrainingDate(trainingDate);
//        training.setTrainingDuration(trainingDuration);
//
//        when(traineeRepository.findByUserUsername(traineeUsername)).thenReturn(trainee);
//        when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(trainer);
//        when(trainingRepository.save(any())).thenReturn(training);
//
//
//        verify(traineeRepository).findByUserUsername(traineeUsername);
//        verify(trainerRepository).findByUserUsername(trainerUsername);
//        verify(trainingRepository).save(any(Training.class));
//
//
//    }
//
////    @Test(expected = IllegalArgumentException.class)
////    public void testCreateTraining_InvalidUsername() throws Exception {
////        // Mock data
////        String username = "invalid_user";
////        String trainerUsername = "trainer1";
////        String trainingName = "My Training";
////        Date trainingDate = new Date();
////        Integer trainingDuration = 60;
////
////        when(traineeRepository.findByUserUsername(username)).thenReturn(null);
////
////        // Call the method (expecting exception)
////        trainingService.createTraining(username, trainerUsername, trainingName, trainingDate, trainingDuration);
////    }
////
////    @Test(expected = IllegalArgumentException.class)
////    public void testCreateTraining_InvalidTrainerUsername() throws Exception {
////        // Mock data
////        String username = "user1";
////        String trainerUsername = "invalid_trainer";
////        String trainingName = "My Training";
////        Date trainingDate = new Date();
////        Integer trainingDuration = 60;
////
////        when(traineeRepository.findByUserUsername(username)).thenReturn(new Trainee());
////        when(trainerRepository.findByUserUsername(trainerUsername)).thenReturn(null);
////
////        // Call the method (expecting exception)
////        trainingService.createTraining(username, trainerUsername, trainingName, trainingDate, trainingDuration);
////    }
//}