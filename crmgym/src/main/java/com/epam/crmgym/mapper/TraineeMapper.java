package com.epam.crmgym.mapper;


import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.repository.TrainingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TraineeMapper {

    private final TrainingRepository trainingRepository;

    public TraineeMapper(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public TraineeProfileDTO mapTraineeToDTO(Trainee trainee) {
        TraineeProfileDTO profileDTO = new TraineeProfileDTO();
        User user = trainee.getUser();
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setDateOfBirth(trainee.getDateOfBirth());
        profileDTO.setAddress(trainee.getAddress());
        profileDTO.setActive(user.isActive());

        List<Training> trainings = trainingRepository.findByTraineeId(trainee.getId());
        List<TrainerDTO> trainers = trainings.stream()
                .map(training -> {
                    TrainerDTO trainerDTO = new TrainerDTO();
                    Trainer trainer = training.getTrainer();
                    trainerDTO.setUsername(trainer.getUser().getUsername());
                    trainerDTO.setFirstName(trainer.getUser().getFirstName());
                    trainerDTO.setLastName(trainer.getUser().getLastName());
                    trainerDTO.setSpecialization(training.getTrainingType().getTrainingType().toString());
                    return trainerDTO;
                })
                .collect(Collectors.toList());

        profileDTO.setTrainers(trainers);

        return profileDTO;
    }
}

