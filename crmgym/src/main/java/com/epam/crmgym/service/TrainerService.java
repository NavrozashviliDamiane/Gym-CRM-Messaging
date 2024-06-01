package com.epam.crmgym.service;

import com.epam.crmgym.dto.trainer.*;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.exception.UsernameValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public interface TrainerService {


    TrainerProfileDTO getTrainerProfile(String username);

    @Transactional
    Trainer createTrainer(TrainerRegistrationRequest request) throws UsernameValidationException;


    @Transactional
    void updateTrainerStatus(String username, boolean isActive);

    List<TrainerDTO> findUnassignedActiveTrainersByTraineeUsername(String traineeUsername);

    @Transactional
    TrainerProfileDTO updateTrainerProfile(TrainerUpdateDTO trainerUpdateDTO);

    @Transactional(readOnly = true)
    List<TrainerTrainingResponseDTO> getTrainerTrainings(TrainerTrainingsRequestDTO request);
}
