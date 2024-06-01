package com.epam.crmgym.util.trainer;


import com.epam.crmgym.dto.trainer.TrainerUpdateDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.repository.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerSpecializationUpdater {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    public void updateSpecialization(Trainer trainer, TrainerUpdateDTO trainerUpdateDTO) {
        String specialization = trainerUpdateDTO.getSpecialization();
        if (specialization != null) {
            TrainingType trainingType = trainingTypeRepository.findByTrainingType(TrainingTypeValue.valueOf(specialization.toUpperCase()));
            if (trainingType != null) {
                trainer.setTrainingType(trainingType);
            }
        }
    }
}

