package com.epam.crmgym.controller;

import com.epam.crmgym.dto.user.UpdateUserStatusRequestDTO;
import com.epam.crmgym.dto.user.UsernameDTO;
import com.epam.crmgym.exception.BindingResultError;
import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.repository.TrainerRepository;
import lombok.extern.slf4j.Slf4j;
import com.epam.crmgym.dto.trainer.*;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    private final AuthenticateService authenticateService;

    private final BindingResultError bindingResultError;

    private final TrainerRepository trainerRepository;

    public TrainerController(TrainerService trainerService,
                             AuthenticateService authenticateService,
                             BindingResultError bindingResultError, TrainerRepository trainerRepository) {
        this.trainerService = trainerService;
        this.authenticateService = authenticateService;
        this.bindingResultError = bindingResultError;
        this.trainerRepository = trainerRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTrainer(@Validated @RequestBody TrainerRegistrationRequest request, BindingResult bindingResult) {
        log.info("REST call made to /api/trainers/register endpoint. Request: {}", request);

        if (bindingResult.hasErrors()) {
            List<String> validationErrors = bindingResultError.handleBindingResultErrors(bindingResult);

            log.error("Validation errors occurred while processing /api/trainers/register endpoint: {}", validationErrors);

            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            Trainer trainer = trainerService.createTrainer(request);
            TrainerRegistrationResponse response = new TrainerRegistrationResponse(
                    trainer.getUser().getUsername(),
                    trainer.getUser().getPassword());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (UsernameValidationException e) {
            log.error("Error occurred while processing /api/trainers/register endpoint: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            log.info("Error occurred while processing /api/trainers/register endpoint: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getTrainerProfile(@Validated @RequestBody UsernameDTO usernameDTO) {
        String username = usernameDTO.getUsername();

        log.info("REST call made to /api/trainers/get-profile endpoint. Request: {}", username);


        TrainerProfileDTO trainerProfile = trainerService.getTrainerProfile(username);
        if (trainerProfile != null) {
            return ResponseEntity.ok(trainerProfile);
        } else {
            log.info("Error occurred while processing /api/trainers/get-profile endpoint.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found");
        }
    }


    @PutMapping
    public ResponseEntity<?> updateTrainerProfile(@Validated @RequestBody TrainerUpdateDTO trainerUpdateDTO) {

        String username = trainerUpdateDTO.getUsername();

        log.info("REST call made to /api/trainers/update-profile endpoint. Request: {}", trainerUpdateDTO);

        TrainerProfileDTO updatedProfile = trainerService.updateTrainerProfile(trainerUpdateDTO);
        if (updatedProfile != null) {
            return ResponseEntity.ok(updatedProfile);
        } else {
            log.info("\"Error occurred while processing /api/trainers/update-profile endpoint.\", e");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trainer not found");
        }
    }

    @PatchMapping
    public ResponseEntity<String> updateTrainerStatus(@Validated @RequestBody UpdateUserStatusRequestDTO requestDTO) {


        log.info("User authenticated successfully");

        Trainer trainer = trainerRepository.findByUserUsername(requestDTO.getUsername());
        if (trainer == null) {
            log.error("Trainer not found with username: " + requestDTO.getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Trainer not found with username: " + requestDTO.getUsername());
        }

        try {
            trainerService.updateTrainerStatus(requestDTO.getUsername(), requestDTO.getIsActive());
            return ResponseEntity.ok("Trainer status updated successfully!");
        } catch (Exception e) {
            log.error("An error occurred while updating trainer status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while updating trainer status");
        }
    }


    @GetMapping("/unassigned-active")
    public ResponseEntity<?> getUnassignedActiveTrainersByTraineeUsername(
            @Validated @RequestBody UsernameDTO usernameDTO) {
        String traineeUsername = usernameDTO.getUsername();

        log.info("REST call made to /api/trainers/unassigned-active endpoint. Request: {} ", traineeUsername );



        List<TrainerDTO> unassignedActiveTrainers = trainerService.findUnassignedActiveTrainersByTraineeUsername(traineeUsername);
        return ResponseEntity.ok(unassignedActiveTrainers);
    }


    @GetMapping("/trainings")
    public ResponseEntity<?> getTrainerTrainings(
            @RequestBody TrainerTrainingsRequestDTO request
    ) {
        String username = request.getUsername();

        log.info("REST call made to /api/trainers/trainings endpoint. Request: {}", username);


        try {
            List<TrainerTrainingResponseDTO> trainings = trainerService.getTrainerTrainings(request);
            return ResponseEntity.ok(trainings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }   catch (Exception e) {
            log.info("Error occurred while processing /api/trainers/trainings endpoint:", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
