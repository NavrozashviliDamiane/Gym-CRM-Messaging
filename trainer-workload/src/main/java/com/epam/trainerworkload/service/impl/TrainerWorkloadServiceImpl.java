package com.epam.trainerworkload.service.impl;

import com.epam.trainerworkload.dto.GetTotalTrainingHoursRequestDTO;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainingSession;
import com.epam.trainerworkload.repository.TrainingSessionRepository;
import com.epam.trainerworkload.service.TrainerWorkloadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerWorkloadServiceImpl implements TrainerWorkloadService {

    private final TrainingSessionRepository repository;


    public TrainerWorkloadServiceImpl(TrainingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void manageTrainingSession(TrainingSessionDTO dto) {
        if ("ADD".equalsIgnoreCase(dto.getActionType())) {
            addTrainingSession(dto);
        } else if ("DELETE".equalsIgnoreCase(dto.getActionType())) {
            deleteTrainingSession(dto);
        }
    }

    private void addTrainingSession(TrainingSessionDTO dto) {
        TrainingSession session = new TrainingSession();
        session.setTrainerUsername(dto.getTrainerUsername());
        session.setFirstName(dto.getFirstName());
        session.setLastName(dto.getLastName());
        session.setIsActive(dto.isActive());
        session.setTrainingDate(dto.getTrainingDate());
        session.setTrainingDuration(dto.getTrainingDuration());
        repository.save(session);
    }

    private void deleteTrainingSession(TrainingSessionDTO dto) {
        List<TrainingSession> sessions = repository.findByTrainerUsernameAndTrainingDate(dto.getTrainerUsername(), dto.getTrainingDate());
        repository.deleteAll(sessions);
    }

    @Override
    public int getTotalTrainingHours(GetTotalTrainingHoursRequestDTO requestDTO) {
        List<TrainingSession> sessions = repository.findTrainingSessionsByTrainerAndMonth(requestDTO.getUsername(), requestDTO.getYear(), requestDTO.getMonth());
        return sessions.stream()
                .mapToInt(TrainingSession::getTrainingDuration)
                .sum();
    }
}
