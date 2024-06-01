package com.epam.crmgym.mapper;

import com.epam.crmgym.dto.trainer.TrainerTrainingResponseDTO;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TrainerTrainingMapper {

    public TrainerTrainingResponseDTO mapTrainingToResponseDTO(Training training) {
        Trainee trainee = training.getTrainee();
        User user = trainee.getUser();
        String traineeUsername = user.getUsername();

        return new TrainerTrainingResponseDTO(
                training.getTrainingName(),
                training.getTrainingDate(),
                training.getTrainingType().getTrainingType().toString(),
                training.getTrainingDuration(),
                traineeUsername
        );
    }
}

