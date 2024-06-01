package com.epam.crmgym.mapper;

import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import org.springframework.stereotype.Component;


@Component
public class TrainingToTrainerMapper {

    public TrainerDTO mapTrainingToTrainerDTO(Training training) {
        TrainerDTO trainerDTO = new TrainerDTO();
        Trainer trainer = training.getTrainer();
        User trainerUser = trainer.getUser();
        trainerDTO.setUsername(trainerUser.getUsername());
        trainerDTO.setFirstName(trainerUser.getFirstName());
        trainerDTO.setLastName(trainerUser.getLastName());
        trainerDTO.setSpecialization(training.getTrainingType().getTrainingType().toString());
        return trainerDTO;
    }

}
