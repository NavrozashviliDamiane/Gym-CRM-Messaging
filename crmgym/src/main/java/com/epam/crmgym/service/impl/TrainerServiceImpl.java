package com.epam.crmgym.service.impl;

import com.epam.crmgym.exception.UsernameValidationException;
import com.epam.crmgym.util.trainer.TrainingQueryConstructor;
import lombok.extern.slf4j.Slf4j;
import com.epam.crmgym.dto.trainee.TraineeDTO;
import com.epam.crmgym.dto.trainer.*;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.mapper.TrainerMapper;
import com.epam.crmgym.mapper.TrainerTrainingMapper;
import com.epam.crmgym.repository.*;
import com.epam.crmgym.service.AuthenticateService;
import com.epam.crmgym.service.TrainerService;
import com.epam.crmgym.service.UserService;
import com.epam.crmgym.util.trainer.TrainerProfileDtoCreator;
import com.epam.crmgym.util.trainer.TrainerSpecializationUpdater;
import com.epam.crmgym.util.user.UserUpdateHelper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainingQueryConstructor queryConstructor;

    private final UserService userService;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final AuthenticateService authenticateService;

    private final TrainerSpecializationUpdater specializationUpdater;

    private final UserUpdateHelper userUpdateHelper;

    private final TrainerTrainingMapper trainerTrainingMapper;

    private final TrainerMapper trainerMapper;

    private final TrainerProfileDtoCreator profileDtoCreator;


    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserService userService,
                              TrainingTypeRepository trainingTypeRepository,
                              TraineeRepository traineeRepository, TrainingRepository trainingRepository,
                              AuthenticateService authenticateService, Training training,
                              TrainerMapper trainerMapper, TrainerTrainingMapper trainerTrainingMapper,
                              UserRepository userRepository, UserUpdateHelper userUpdateHelper,
                              TrainerSpecializationUpdater specializationUpdater,
                              TrainerProfileDtoCreator profileDtoCreator, TrainingQueryConstructor queryConstructor) {
        this.trainerRepository = trainerRepository;
        this.userService = userService;
        this.trainingTypeRepository = trainingTypeRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.authenticateService = authenticateService;
        this.trainerMapper = trainerMapper;
        this.trainerTrainingMapper = trainerTrainingMapper;
        this.userUpdateHelper = userUpdateHelper;
        this.specializationUpdater = specializationUpdater;
        this.profileDtoCreator = profileDtoCreator;
        this.queryConstructor = queryConstructor;
    }







    @Override
    @Transactional
    public TrainerProfileDTO getTrainerProfile(String username) {

        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for getting trainer profile. Transaction ID: {}", transactionId);

        Trainer trainer = trainerRepository.findByUserUsername(username);
        if (trainer == null) {
            return null;
        }

        TrainerProfileDTO profileDTO = new TrainerProfileDTO();
        User user = trainer.getUser();
        profileDTO.setUsername(user.getUsername());
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setIsActive(user.isActive());
        TrainingType trainingType = trainer.getTrainingType();
        if (trainingType != null) {
            profileDTO.setSpecialization(trainingType.getTrainingType().toString());
        }
        log.info("Transaction finished to retrieve user from trainer entity. Transaction ID: {}", transactionId);


        List<Training> trainings = trainingRepository.findByTrainerId(trainer.getId());
        List<TraineeDTO> trainees = trainings.stream()
                .map(training -> {
                    Trainee trainee = training.getTrainee();
                    TraineeDTO traineeDTO = new TraineeDTO();
                    traineeDTO.setUsername(trainee.getUser().getUsername());
                    traineeDTO.setFirstName(trainee.getUser().getFirstName());
                    traineeDTO.setLastName(trainee.getUser().getLastName());
                    return traineeDTO;
                })
                .collect(Collectors.toList());

        profileDTO.setTrainees(trainees);

        log.info("Transaction successful to retrieve trainer profile. Transaction ID: {}", transactionId);
        return profileDTO;
    }




    @Override
    @Transactional
    public Trainer createTrainer(TrainerRegistrationRequest request) throws UsernameValidationException {

        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for trainee creation. Transaction ID: {}", transactionId);

        try {
            User user = userService.createUser(request.getFirstName(), request.getLastName());

            TrainingTypeValue trainingTypeValue = TrainingTypeValue.valueOf(request.getSpecialization().toUpperCase());
            TrainingType trainingType = new TrainingType(trainingTypeValue);

            TrainingType existingTrainingType = trainingTypeRepository.findByTrainingType(trainingType.getTrainingType());
            if (existingTrainingType != null) {
                trainingType = existingTrainingType;
            } else {
                trainingType = trainingTypeRepository.save(trainingType);
            }

            Trainer trainer = new Trainer();
            trainer.setUser(user);
            trainer.setTrainingType(trainingType);

            log.info("Trainer created Successfully");
            return trainerRepository.save(trainer);

        } catch (Exception e) {
            log.info("Error occurred while creating trainer. Transaction ID: {}", transactionId, e);
            throw e;
        }
    }


    @Override
    @Transactional
    public void updateTrainerStatus(String username, boolean isActive) {
        Trainer trainer = trainerRepository.findByUserUsername(username);
        if (trainer != null) {
            User user = trainer.getUser();
            user.setActive(isActive);
            userService.saveUser(user);

            log.info("Trainer status updated successfully!");
        } else {
            log.error("Trainer not found with username: " + username);
        }
    }


    @Override
    public List<TrainerDTO> findUnassignedActiveTrainersByTraineeUsername(String traineeUsername) {

        log.info("User Authenticated Successfully");

        Trainee trainee = traineeRepository.findByUserUsername(traineeUsername);
        List<Training> trainingsWithTrainee = trainingRepository.findByTraineeId(trainee.getId());

        log.info("Unassigned Active Trainer found successfully");
        return trainerRepository.findAll().stream()
                .filter(trainer -> trainingsWithTrainee.stream()
                        .noneMatch(training -> Objects.equals(training.getTrainer(), trainer)))
                .filter(trainer -> trainer.getUser().isActive())
                .map(trainerMapper::convertToTrainerDTO)
                .collect(Collectors.toList());
    }



    @Override
    public TrainerProfileDTO updateTrainerProfile(TrainerUpdateDTO trainerUpdateDTO) {
        String username = trainerUpdateDTO.getUsername();

        Trainer trainer = trainerRepository.findByUserUsername(username);


        User user = trainer.getUser();
        userUpdateHelper.updateUser(user, trainerUpdateDTO);
        specializationUpdater.updateSpecialization(trainer, trainerUpdateDTO);

        return profileDtoCreator.createTrainerProfileDTO(trainer);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TrainerTrainingResponseDTO> getTrainerTrainings(TrainerTrainingsRequestDTO request) {
        try {
            String username = request.getUsername();
            log.info("Fetching trainer trainings for username: {}", username);

            Trainee trainee = null;
            if (request.getTraineeName() != null) {
                trainee = traineeRepository.findByUserUsername(request.getTraineeName());
                if (trainee == null) {
                    log.warn("No trainee found for username: {}", request.getTraineeName());
                    return Collections.emptyList();
                }
                log.debug("Trainee found with ID: {}", trainee.getId());
            }

            Trainer trainer = null;
            if (request.getUsername() != null) {
                trainer = trainerRepository.findByUserUsername(request.getUsername());
                if (trainer == null) {
                    log.warn("No trainer found for username: {}", username);
                    return Collections.emptyList();
                }
                log.debug("Trainer found with ID: {}", trainer.getId());
            }

            List<Training> trainings = queryConstructor.constructQuery(
                    trainer != null ? trainer.getId() : null,
                    request.getPeriodFrom(),
                    request.getPeriodTo() != null ? DateUtils.addDays(request.getPeriodTo(), 1) : null,
                    trainee != null ? trainee.getId() : null
            );

            log.info("Found {} trainings for username: {}", trainings.size(), username);

            return trainings.stream()
                    .map(trainerTrainingMapper::mapTrainingToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching trainer trainings for username: {}", request.getUsername(), e);
            throw new RuntimeException("Error occurred while fetching trainer trainings", e);
        }
    }


}



