package com.epam.crmgym.controller;

import com.epam.crmgym.dto.trainer.*;
import com.epam.crmgym.dto.user.UpdateUserStatusRequestDTO;
import com.epam.crmgym.dto.user.UsernameDTO;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;



    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        trainerService = mock(TrainerService.class);
        trainerRepository = mock(TrainerRepository.class);
        trainerController = new TrainerController(trainerService, null, null, trainerRepository);
    }



    @Test
    void testGetTrainerProfile_Successful() {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername("trainer1");
        when(trainerService.getTrainerProfile(any())).thenReturn(new TrainerProfileDTO());

        ResponseEntity<?> responseEntity = trainerController.getTrainerProfile(usernameDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTrainerProfile_Successful() {
        TrainerUpdateDTO trainerUpdateDTO = new TrainerUpdateDTO();
        trainerUpdateDTO.setUsername("trainer1");
        when(trainerService.updateTrainerProfile(any())).thenReturn(new TrainerProfileDTO());

        ResponseEntity<?> responseEntity = trainerController.updateTrainerProfile(trainerUpdateDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTrainerStatus_Successful() {
        UpdateUserStatusRequestDTO requestDTO = new UpdateUserStatusRequestDTO();
        requestDTO.setUsername("trainer1");
        requestDTO.setIsActive(true);
        when(trainerRepository.findByUserUsername(any())).thenReturn(new Trainer());

        ResponseEntity<String> responseEntity = trainerController.updateTrainerStatus(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetUnassignedActiveTrainersByTraineeUsername_Successful() {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername("trainee1");
        when(trainerService.findUnassignedActiveTrainersByTraineeUsername(any())).thenReturn(Collections.emptyList());

        ResponseEntity<?> responseEntity = trainerController.getUnassignedActiveTrainersByTraineeUsername(usernameDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainerTrainings_Successful() {
        TrainerTrainingsRequestDTO requestDTO = new TrainerTrainingsRequestDTO();
        requestDTO.setUsername("trainer1");
        when(trainerService.getTrainerTrainings(any())).thenReturn(Collections.emptyList());

        ResponseEntity<?> responseEntity = trainerController.getTrainerTrainings(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
