package com.epam.trainerworkload.controller;

import com.epam.trainerworkload.dto.GetTotalTrainingHoursRequestDTO;
import com.epam.trainerworkload.dto.TrainingSessionDTO;
import com.epam.trainerworkload.service.TrainerWorkloadService;
import com.epam.trainerworkload.util.transaction.TransactionIdGenerator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/workload")
@Validated
public class TrainerWorkloadController {

    private final TrainerWorkloadService service;

    @Autowired
    public TrainerWorkloadController(TrainerWorkloadService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> manageTrainingSession(@Valid @RequestBody TrainingSessionDTO dto) {
        String transactionId = TransactionIdGenerator.generateTransactionId();
        log.info("Transaction ID: {} - Received request to get create Session",
                transactionId);
        service.manageTrainingSession(dto);
        return ResponseEntity.ok("Session processed successfully");
    }


    @GetMapping("/total-hours")
    public ResponseEntity<Integer> getTotalTrainingHours(@Valid @RequestBody GetTotalTrainingHoursRequestDTO requestDTO) {
        String transactionId = TransactionIdGenerator.generateTransactionId();
        log.info("Transaction ID: {} - Received request to get total training hours for username: {}, year: {}, month: {}",
                transactionId, requestDTO.getUsername(), requestDTO.getYear(), requestDTO.getMonth());
        int totalHours = service.getTotalTrainingHours(requestDTO);
        return ResponseEntity.ok(totalHours);
    }
}
