package com.epam.crmgym.service;


import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrainingTypeService {
    TrainingType createTrainingType(TrainingTypeValue trainingTypeValue);

    @Transactional(readOnly = true)
    List<TrainingType> getAllTrainingTypes();
}
