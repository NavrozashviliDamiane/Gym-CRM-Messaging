package com.epam.crmgym.service;

import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerResponse;
import com.epam.crmgym.dto.training.TrainingDTO;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.exception.EntityNotFoundException;
import com.epam.crmgym.exception.UsernameValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public interface TraineeService {

    TraineeProfileDTO getTraineeProfile(String username);


    @Transactional
    Trainee updateTraineeProfile(String username, String firstName, String lastName,
                                 Date dateOfBirth, String address, Boolean isActive);

    Trainee createTrainee(String firstName, String lastName, Date dateOfBirth, String address) throws UsernameValidationException;



    @Transactional
    void updateTraineeStatus(String username, boolean isActive);


    @Transactional
    void deleteTraineeByUsername(String username);

    List<TrainingDTO> getTraineeTrainingsList(String username, Date fromDate, Date toDate,
                                              String trainerName, String trainingTypeName);


    List<TrainerResponse> updateTraineeTrainersList(String username, List<String> trainerUsernames) throws EntityNotFoundException;
}
