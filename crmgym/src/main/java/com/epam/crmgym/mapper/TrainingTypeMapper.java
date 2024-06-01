package com.epam.crmgym.mapper;


import com.epam.crmgym.dto.training.TrainingTypeDTO;
import com.epam.crmgym.entity.TrainingType;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeMapper {

    public TrainingTypeDTO mapToTrainingTypeDTO(TrainingType trainingType) {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setTrainingType(trainingType.getTrainingType().toString());
        trainingTypeDTO.setTrainingTypeId(trainingType.getId());
        return trainingTypeDTO;
    }
}

