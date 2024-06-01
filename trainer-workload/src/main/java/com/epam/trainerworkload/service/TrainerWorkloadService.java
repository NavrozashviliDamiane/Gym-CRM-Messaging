package com.epam.trainerworkload.service;

import com.epam.trainerworkload.dto.GetTotalTrainingHoursRequestDTO;
import com.epam.trainerworkload.dto.TrainingSessionDTO;

public interface TrainerWorkloadService {
    void manageTrainingSession(TrainingSessionDTO dto);
    int getTotalTrainingHours(GetTotalTrainingHoursRequestDTO requestDTO);
}
