package com.epam.crmgym.controller;

import com.epam.crmgym.dto.training.TrainingTypeDTO;
import com.epam.crmgym.dto.user.UserCredentialsDTO;
import com.epam.crmgym.dto.user.UsernameDTO;
import com.epam.crmgym.entity.TrainingType;
import com.epam.crmgym.mapper.TrainingTypeMapper;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.TrainingTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("api/training-types")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;
    private final TrainingTypeMapper trainingTypeMapper;

    private final AuthenticateService authenticateService;

    @Autowired
    public TrainingTypeController(TrainingTypeService trainingTypeService,
                                  TrainingTypeMapper trainingTypeMapper,
                                  AuthenticateService authenticateService) {
        this.trainingTypeService = trainingTypeService;
        this.trainingTypeMapper = trainingTypeMapper;
        this.authenticateService = authenticateService;
    }

    @GetMapping
    public ResponseEntity<?> getTrainingTypes(@Validated @RequestBody UsernameDTO UsernameDTO) {


        try {
            List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
            List<TrainingTypeDTO> trainingTypeDTOs = trainingTypes.stream()
                    .map(trainingTypeMapper::mapToTrainingTypeDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(trainingTypeDTOs);
        } catch (Exception e) {
            log.error("An error occurred while fetching training types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while fetching training types");
        }
    }



}

