package com.epam.crmgym.controller;

import com.epam.crmgym.dto.training.TrainingRequest;
import com.epam.crmgym.service.TrainingService;


import com.epam.crmgym.util.transaction.TransactionIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<String> addTraining(@Validated @RequestBody TrainingRequest trainingRequest) {
        String transactionId = TransactionIdGenerator.generateTransactionId();
        log.info("Transaction ID: {} - Received request to add training: {}", transactionId, trainingRequest);

        try {
            trainingService.createTraining(trainingRequest.getUsername(),
                    trainingRequest.getTrainerUsername(), trainingRequest.getTrainingName(), trainingRequest.getTrainingDate(), trainingRequest.getTrainingDuration());
            log.info("Transaction ID: {} - Training created successfully!", transactionId);
            return ResponseEntity.ok("Training created successfully!");
        } catch (Exception e) {
            log.error("Transaction ID: {} - Failed to create training: {}", transactionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create training: " + e.getMessage());
        }
    }
}

