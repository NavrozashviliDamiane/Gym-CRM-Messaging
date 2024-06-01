package com.epam.crmgym.controller;

import com.epam.crmgym.dto.training.TrainingTypeDTO;
import com.epam.crmgym.dto.user.UsernameDTO;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.mapper.TrainingTypeMapper;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.TrainingTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainingTypeMapper trainingTypeMapper;

    @Mock
    private AuthenticateService authenticateService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @Test
    void testGetTrainingTypes_Successful() {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername("username");
        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType());
        List<TrainingTypeDTO> trainingTypeDTOs = new ArrayList<>();
        trainingTypeDTOs.add(new TrainingTypeDTO());

        when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypes);
        when(trainingTypeMapper.mapToTrainingTypeDTO(any())).thenReturn(new TrainingTypeDTO());

        ResponseEntity<?> responseEntity = trainingTypeController.getTrainingTypes(usernameDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(trainingTypeService, times(1)).getAllTrainingTypes();
        verify(trainingTypeMapper, times(1)).mapToTrainingTypeDTO(any());
    }

    @Test
    void testGetTrainingTypes_InternalServerError() {
        UsernameDTO usernameDTO = new UsernameDTO();
        usernameDTO.setUsername("username");

        when(trainingTypeService.getAllTrainingTypes()).thenThrow(new RuntimeException());

        ResponseEntity<?> responseEntity = trainingTypeController.getTrainingTypes(usernameDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An unexpected error occurred while fetching training types", responseEntity.getBody());
        verify(trainingTypeService, times(1)).getAllTrainingTypes();
        verify(trainingTypeMapper, never()).mapToTrainingTypeDTO(any());
    }



}
