package com.epam.crmgym.controller;


import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainee.TraineeRegistrationDTO;
import com.epam.crmgym.dto.trainee.TraineeUpdateDTO;
import com.epam.crmgym.dto.trainee.UpdateTraineeTrainerListRequestDTO;
import com.epam.crmgym.dto.user.UpdateUserStatusRequestDTO;
import com.epam.crmgym.dto.user.UsernameDTO;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.BindingResultError;
import com.epam.crmgym.exception.EntityNotFoundException;
import com.epam.crmgym.exception.TraineeNotFoundException;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.TraineeRepository;
import com.epam.crmgym.service.TraineeService;
import com.epam.crmgym.dto.user.UserCredentialsDTO;
import com.epam.crmgym.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserService userService;

    @Mock
    private BindingResultError bindingResultError;

    @InjectMocks
    private TraineeController traineeController;

    @Test
    void testRegisterTrainee_Successful() throws UsernameValidationException {
        TraineeRegistrationDTO registrationDTO = new TraineeRegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setDateOfBirth(new Date());
        registrationDTO.setAddress("123 Street");

        Trainee createdTrainee = new Trainee();
        createdTrainee.setId(1L);

        User user = new User();
        user.setUsername("john.doe");
        createdTrainee.setUser(user);

        UserCredentialsDTO expectedCredentials = new UserCredentialsDTO();
        expectedCredentials.setUsername("john.doeler");
        expectedCredentials.setPassword("passwordpass");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(traineeService.createTrainee(anyString(), anyString(), any(Date.class), anyString())).thenReturn(createdTrainee);

        ResponseEntity<?> responseEntity = traineeController.registerTrainee(registrationDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void testRegisterTrainee_FailedValidation() throws UsernameValidationException {
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> responseEntity = traineeController.registerTrainee(new TraineeRegistrationDTO(), bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


    @Test
    void testGetTraineeProfile_ValidUsername() {
        UsernameDTO usernameDTO = new UsernameDTO("john.doe");
        TraineeProfileDTO expectedProfileDTO = new TraineeProfileDTO();
        expectedProfileDTO.setFirstName("John");
        expectedProfileDTO.setLastName("Doe");

        when(traineeService.getTraineeProfile(usernameDTO.getUsername())).thenReturn(expectedProfileDTO);

        ResponseEntity<?> responseEntity = traineeController.getTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProfileDTO, responseEntity.getBody());
    }

    @Test
    void testGetTraineeProfile_InvalidUsername() {
        UsernameDTO usernameDTO = new UsernameDTO(null);
        BindingResult bindingResult = new BeanPropertyBindingResult(usernameDTO, "usernameDTO");
        bindingResult.addError(new ObjectError("usernameDTO", "Username cannot be null"));

        ResponseEntity<?> responseEntity = traineeController.getTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetTraineeProfile_ProfileNotFound() {
        UsernameDTO usernameDTO = new UsernameDTO("john.doe");

        when(traineeService.getTraineeProfile(usernameDTO.getUsername())).thenReturn(null);

        ResponseEntity<?> responseEntity = traineeController.getTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Trainee profile not found", responseEntity.getBody());
    }

    @Test
    void testGetTraineeProfile_InternalServerError() {
        UsernameDTO usernameDTO = new UsernameDTO("john.doe");

        when(traineeService.getTraineeProfile(usernameDTO.getUsername())).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = traineeController.getTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error", responseEntity.getBody());
    }


    @Test
    void testUpdateTraineeProfile_NotFound() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        updateDTO.setUsername("john.doe");

        when(traineeService.updateTraineeProfile(updateDTO.getUsername(), null, null, null, null, null))
                .thenReturn(null);

        ResponseEntity<?> responseEntity = traineeController.updateTraineeProfile(updateDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    @Test
    void testUpdateTraineeProfile_InternalServerError() {
        TraineeUpdateDTO updateDTO = new TraineeUpdateDTO();
        updateDTO.setUsername("john.doe");

        when(traineeService.updateTraineeProfile(updateDTO.getUsername(), null, null, null, null, null))
                .thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = traineeController.updateTraineeProfile(updateDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred while processing the request", responseEntity.getBody());
    }


    @Test
    public void testDeleteTraineeProfile_Success() {
        UsernameDTO usernameDTO = new UsernameDTO("testUser");

        doNothing().when(traineeService).deleteTraineeByUsername(usernameDTO.getUsername());

        ResponseEntity<?> response = traineeController.deleteTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Trainee profile deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteTraineeProfile_Exception() {
        UsernameDTO usernameDTO = new UsernameDTO("testUser");

        doThrow(RuntimeException.class).when(traineeService).deleteTraineeByUsername(usernameDTO.getUsername());

        ResponseEntity<?> response = traineeController.deleteTraineeProfile(usernameDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while processing the request", response.getBody());
    }

    @Test
    public void testUpdateTraineeStatus_Success() {
        UpdateUserStatusRequestDTO updateUserStatusRequestDTO = new UpdateUserStatusRequestDTO("testUser", true);

        when(traineeRepository.findByUserUsername(updateUserStatusRequestDTO.getUsername())).thenReturn(new Trainee());

        doNothing().when(traineeService).updateTraineeStatus(updateUserStatusRequestDTO.getUsername(),
                updateUserStatusRequestDTO.getIsActive());

        ResponseEntity<String> response = traineeController.updateTraineeStatus(updateUserStatusRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Trainee status updated successfully!", response.getBody());
    }

    @Test
    public void testUpdateTraineeStatus_TraineeNotFound() {
        UpdateUserStatusRequestDTO updateUserStatusRequestDTO = new UpdateUserStatusRequestDTO("nonExistingUser", true);

        when(traineeRepository.findByUserUsername(updateUserStatusRequestDTO.getUsername())).thenReturn(null);

        ResponseEntity<String> response = traineeController.updateTraineeStatus(updateUserStatusRequestDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trainee not found with username: " + updateUserStatusRequestDTO.getUsername(),
                response.getBody());
    }

    @Test
    public void testUpdateTraineeStatus_Exception() {
        UpdateUserStatusRequestDTO updateUserStatusRequestDTO = new UpdateUserStatusRequestDTO("testUser", true);

        when(traineeRepository.findByUserUsername(updateUserStatusRequestDTO.getUsername())).thenReturn(new Trainee());

        doThrow(RuntimeException.class).when(traineeService).updateTraineeStatus(updateUserStatusRequestDTO.getUsername(),
                updateUserStatusRequestDTO.getIsActive());

        ResponseEntity<String> response = traineeController.updateTraineeStatus(updateUserStatusRequestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred while updating trainee status", response.getBody());
    }


    @Test
    void testUpdateTraineeTrainerList_Successful() throws EntityNotFoundException {
        UpdateTraineeTrainerListRequestDTO requestDTO = new UpdateTraineeTrainerListRequestDTO();
        requestDTO.setUsername("testUser");
        requestDTO.setTrainerUsernames(Collections.singletonList("trainer1"));

        when(traineeService.updateTraineeTrainersList(eq("testUser"), anyList())).thenReturn(Collections.emptyList());

        ResponseEntity<?> responseEntity = traineeController.updateTraineeTrainerList(requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTraineeTrainerList_TraineeNotFound() throws EntityNotFoundException {
        UpdateTraineeTrainerListRequestDTO requestDTO = new UpdateTraineeTrainerListRequestDTO();
        requestDTO.setUsername("nonExistingUser");

        when(traineeService.updateTraineeTrainersList(eq("nonExistingUser"), anyList())).thenThrow(TraineeNotFoundException.class);

        ResponseEntity<?> responseEntity = traineeController.updateTraineeTrainerList(requestDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }



}
