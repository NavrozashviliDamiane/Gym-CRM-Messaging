package com.epam.crmgym.controller;

import com.epam.crmgym.controller.TrainingController;
import com.epam.crmgym.dto.training.TrainingRequest;
import com.epam.crmgym.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    void setUp() {
        trainingService = mock(TrainingService.class);
        trainingController = new TrainingController(trainingService);
    }

    @Test
    void testAddTraining_Successful() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setUsername("user1");
        trainingRequest.setTrainerUsername("trainer1");
        trainingRequest.setTrainingName("Training 1");
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        trainingRequest.setTrainingDate(date);
        trainingRequest.setTrainingDuration(60);

        when(trainingService.createTraining(any(), any(), any(), any(), anyInt())).thenReturn(null);

        ResponseEntity<String> responseEntity = trainingController.addTraining(trainingRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Training created successfully!", responseEntity.getBody());
        verify(trainingService, times(1)).createTraining(any(), any(), any(), any(), anyInt());
    }

    @Test
    void testAddTraining_Failure() {
        TrainingRequest trainingRequest = new TrainingRequest();
        trainingRequest.setUsername("user1");
        trainingRequest.setTrainerUsername("trainer1");
        trainingRequest.setTrainingName("Training 1");
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        trainingRequest.setTrainingDate(date);
        trainingRequest.setTrainingDuration(60);

        doThrow(new RuntimeException("Failed to create training")).when(trainingService).createTraining(anyString(), anyString(), anyString(), any(Date.class), anyInt());

        ResponseEntity<String> responseEntity = trainingController.addTraining(trainingRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to create training: Failed to create training", responseEntity.getBody());
    }
}
