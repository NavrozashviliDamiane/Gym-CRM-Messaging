package com.epam.trainerworkload.service.impl;

import com.epam.trainerworkload.dto.GetTotalTrainingHoursRequestDTO;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.entity.TrainingSession;
import com.epam.trainerworkload.repository.TrainingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerWorkloadServiceImplTest {

    @Mock
    private TrainingSessionRepository repository;

    @InjectMocks
    private TrainerWorkloadServiceImpl service;

    private TrainingSessionDTO trainingSessionDTO;
    private GetTotalTrainingHoursRequestDTO getTotalTrainingHoursRequestDTO;

    @BeforeEach
    void setUp() {
        trainingSessionDTO = new TrainingSessionDTO(
                "john_doe",
                "John",
                "Doe",
                true,
                new Date(),
                60,
                "ADD"
        );

        getTotalTrainingHoursRequestDTO = new GetTotalTrainingHoursRequestDTO(
                "john_doe",
                2024,
                5
        );
    }

    @Test
    void manageTrainingSession_addSession_success() {
        service.manageTrainingSession(trainingSessionDTO);

        verify(repository, times(1)).save(any(TrainingSession.class));
    }

    @Test
    void manageTrainingSession_deleteSession_success() {
        trainingSessionDTO.setActionType("DELETE");
        List<TrainingSession> sessions = new ArrayList<>();
        when(repository.findByTrainerUsernameAndTrainingDate(anyString(), any(Date.class))).thenReturn(sessions);

        service.manageTrainingSession(trainingSessionDTO);

        verify(repository, times(1)).deleteAll(sessions);
    }

    @Test
    void getTotalTrainingHours_success() {
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(new TrainingSession(1L, "john_doe", "John", "Doe", true, new Date(), 60));
        sessions.add(new TrainingSession(2L, "john_doe", "John", "Doe", true, new Date(), 90));
        when(repository.findTrainingSessionsByTrainerAndMonth(anyString(), anyInt(), anyInt())).thenReturn(sessions);

        int totalHours = service.getTotalTrainingHours(getTotalTrainingHoursRequestDTO);

        assertEquals(150, totalHours);
    }
}
