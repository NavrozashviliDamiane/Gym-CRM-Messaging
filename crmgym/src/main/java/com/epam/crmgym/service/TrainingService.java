package com.epam.crmgym.service;

import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.TrainingTypeValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public interface TrainingService {



    @Transactional
    Training createTraining(String username, String trainerUsername,
                            String trainingName, Date trainingDate, Integer trainingDuration);


}
