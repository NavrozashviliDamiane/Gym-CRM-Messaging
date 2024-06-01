package com.epam.crmgym.util.trainer;

import com.epam.crmgym.dto.trainee.TraineeDTO;
import com.epam.crmgym.dto.trainer.TrainerProfileDTO;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainerProfileDtoCreator {

    @Autowired
    private TrainingRepository trainingRepository;

    public TrainerProfileDTO createTrainerProfileDTO(Trainer trainer) {
        TrainerProfileDTO profileDTO = new TrainerProfileDTO();
        User user = trainer.getUser();
        profileDTO.setUsername(user.getUsername());
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setIsActive(user.isActive());

        TrainingType trainingType = trainer.getTrainingType();
        if (trainingType != null) {
            profileDTO.setSpecialization(trainingType.getTrainingType().toString());
        }

        List<Training> trainings = trainingRepository.findByTrainerId(trainer.getId());
        List<TraineeDTO> trainees = trainings.stream()
                .map(training -> {
                    Trainee trainee = training.getTrainee();
                    TraineeDTO traineeDTO = new TraineeDTO();
                    User traineeUser = trainee.getUser();
                    traineeDTO.setUsername(traineeUser.getUsername());
                    traineeDTO.setFirstName(traineeUser.getFirstName());
                    traineeDTO.setLastName(traineeUser.getLastName());
                    return traineeDTO;
                })
                .collect(Collectors.toList());

        profileDTO.setTrainees(trainees);
        return profileDTO;
    }
}

