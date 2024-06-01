package com.epam.crmgym.util.trainee;

import com.epam.crmgym.dto.training.TrainingDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.entity.TrainingTypeValue;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.repository.TrainingTypeRepository;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetTraineeTrainingsHelper {

    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingRepository trainingRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");


    public GetTraineeTrainingsHelper(TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository, TrainingRepository trainingRepository) {
        this.trainerRepository = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingRepository = trainingRepository;
    }

    public Long getTrainerId(String trainerName) {
        if (trainerName != null) {
            Trainer trainer = trainerRepository.findByUserUsername(trainerName);
            if (trainer != null) {
                return trainer.getId();
            }
        }
        return null;

    }

    public Long getTrainingTypeId(String trainingTypeName) {
        if (trainingTypeName != null) {
            TrainingType trainingType = trainingTypeRepository.findByTrainingType(TrainingTypeValue.valueOf(trainingTypeName));
            if (trainingType != null) {
                return trainingType.getId();
            }
        }
        return null;
    }

    public List<Training> constructQuery(Long traineeId, Date fromDate, Date toDate, Long trainerId, Long trainingTypeId) {
        if (traineeId == null) {
            throw new IllegalArgumentException("Trainee ID cannot be null.");
        }

        List<Training> trainings;

        if (fromDate != null && toDate != null && fromDate.after(toDate)) {
            throw new IllegalArgumentException("From Date cannot be after To Date.");
        }

        if (fromDate != null && toDate != null) {
            if (trainerId != null && trainingTypeId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtBtwnAndTrIdAndTyIdAndDurGt(traineeId, fromDate, toDate, trainerId, trainingTypeId, 0);
            } else if (trainerId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtBtwnAndTrIdAndDurGt(traineeId, fromDate, toDate, trainerId, 0);
            } else if (trainingTypeId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtBtwnAndTyIdAndDurGt(traineeId, fromDate, toDate, trainingTypeId, 0);
            } else {
                trainings = trainingRepository.findTrByTnIdAndDtBtwnAndDurGt(traineeId, fromDate, toDate, 0);
            }
        } else if (toDate == null && fromDate != null) {
            if (trainerId != null && trainingTypeId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtGtEqAndTrIdAndTyIdAndDurGt(traineeId, fromDate, trainerId, trainingTypeId, 0);
            } else if (trainerId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtGtEqAndTrIdAndDurGt(traineeId, fromDate, trainerId, 0);
            } else if (trainingTypeId != null) {
                trainings = trainingRepository.findTrByTnIdAndDtGtEqAndTyIdAndDurGt(traineeId, fromDate, trainingTypeId, 0);
            } else {
                trainings = trainingRepository.findTrByTnIdAndDtGtEqAndDurGt(traineeId, fromDate, 0);
            }
        } else if (trainerId != null && trainingTypeId != null) {
            trainings = trainingRepository.findByTraineeIdAndTrainerIdAndTrainingTypeId(traineeId, trainerId, trainingTypeId);
        } else if (trainerId != null) {
            trainings = trainingRepository.findByTraineeIdAndTrainerId(traineeId, trainerId);
        } else if (trainingTypeId != null) {
            trainings = trainingRepository.findByTraineeIdAndTrainingTypeId(traineeId, trainingTypeId);
        } else {
            trainings = trainingRepository.findByTraineeId(traineeId);
        }

        return trainings;
    }

    public List<TrainingDTO> mapToTrainingDTO(List<Training> trainings) {
        return trainings.stream().map(training -> {
            TrainingDTO trainingDTO = new TrainingDTO();
            trainingDTO.setTrainingName(training.getTrainingName());
            trainingDTO.setTrainingDate(training.getTrainingDate());
            if (training.getTrainingType() != null) {
                trainingDTO.setTrainingType(training.getTrainingType().getTrainingType().toString());
            } else {
                trainingDTO.setTrainingType("Unknown");
            }
            trainingDTO.setTrainingDuration(training.getTrainingDuration());
            if (training.getTrainer() != null && training.getTrainer().getUser() != null) {
                trainingDTO.setTrainerName(training.getTrainer().getUser().getFirstName() + " " + training.getTrainer().getUser().getLastName());
            } else {
                trainingDTO.setTrainerName("Unknown");
            }
            return trainingDTO;
        }).collect(Collectors.toList());
    }
}

