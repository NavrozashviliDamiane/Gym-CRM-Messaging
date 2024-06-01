package com.epam.crmgym.util.trainer;

import com.epam.crmgym.entity.Training;
import com.epam.crmgym.repository.TrainingRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TrainingQueryConstructor {

    private final TrainingRepository trainingRepository;

    public TrainingQueryConstructor(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    public List<Training> constructQuery(Long trainerId, Date fromDate, Date toDate, Long traineeId) {
        if (trainerId == null) {
            throw new IllegalArgumentException("Trainer ID cannot be null.");
        }

        List<Training> queryResult;

        if (fromDate != null && toDate != null && traineeId != null) {
            queryResult = trainingRepository.findByTrainerIdAndTrainingDateBetweenAndTraineeId(trainerId, fromDate, toDate, traineeId);
        } else if (fromDate != null && toDate != null) {
            queryResult = trainingRepository.findByTrainerIdAndTrainingDateBetween(trainerId, fromDate, toDate);
        } else if (fromDate != null) {
            queryResult = trainingRepository.findByTrainerIdAndTrainingDateAfter(trainerId, fromDate);
        } else if (toDate != null) {
            queryResult = trainingRepository.findByTrainerIdAndTrainingDateBefore(trainerId, toDate);
        } else if (traineeId != null) {
            queryResult = trainingRepository.findByTrainerIdAndTraineeId(trainerId, traineeId);
        } else {
            queryResult = trainingRepository.findByTrainerId(trainerId);
        }

        return queryResult;
    }

}
