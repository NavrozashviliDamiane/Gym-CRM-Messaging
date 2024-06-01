package com.epam.crmgym.util.trainee;

import com.epam.crmgym.dto.trainer.TrainerResponse;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.repository.TrainerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateTraineeTrainersListHelper {

    public List<TrainerResponse> getUpdatedTrainers(List<Training> trainings, TrainerRepository trainerRepository) {
        List<TrainerResponse> updatedTrainers = new ArrayList<>();
        for (Training training : trainings) {
            if (training.getTrainer() != null) {
                Trainer trainer = trainerRepository.findById(training.getTrainer().getId()).orElse(null);
                if (trainer != null) {
                    TrainerResponse trainerResponse = new TrainerResponse();
                    trainerResponse.setUsername(trainer.getUser().getUsername());
                    trainerResponse.setFirstName(trainer.getUser().getFirstName());
                    trainerResponse.setLastName(trainer.getUser().getLastName());
                    trainerResponse.setSpecialization(training.getTrainingType().getTrainingType().toString());
                    updatedTrainers.add(trainerResponse);
                }
            }
        }
        return updatedTrainers;
    }
}
