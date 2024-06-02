package com.epam.crmgym.service.impl;

import com.epam.crmgym.config.activemq.MessageProducer;
import com.epam.crmgym.dto.client.TrainingSessionDTO;
import com.epam.crmgym.dto.trainee.TraineeProfileDTO;
import com.epam.crmgym.dto.trainer.TrainerDTO;
import com.epam.crmgym.dto.trainer.TrainerResponse;
import com.epam.crmgym.dto.training.TrainingDTO;
import com.epam.crmgym.entity.Trainee;
import com.epam.crmgym.entity.Trainer;
import com.epam.crmgym.entity.Training;
import com.epam.crmgym.entity.User;
import com.epam.crmgym.exception.*;
import com.epam.crmgym.mapper.TrainingToTrainerMapper;
import com.epam.crmgym.repository.TraineeRepository;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.service.TraineeService;
import com.epam.crmgym.service.UserService;
import com.epam.crmgym.util.trainee.GetTraineeTrainingsHelper;
import com.epam.crmgym.util.trainee.UpdateTraineeTrainersListHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final UserService userService;


    private final TrainingToTrainerMapper trainingToTrainerMapper;

    private final GetTraineeTrainingsHelper getTraineeTrainingsHelper;


    private final UpdateTraineeTrainersListHelper updateTraineeTrainersListHelper;

    private final TrainerRepository trainerRepository;

    private final TrainingRepository trainingRepository;

    private final MessageProducer messageProducer;




    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, UserService userService,
                              TrainingRepository trainingRepository,
                              TrainerRepository trainerRepository,
                              TrainingToTrainerMapper trainingToTrainerMapper,
                              GetTraineeTrainingsHelper getTraineeTrainingsHelper, UpdateTraineeTrainersListHelper updateTraineeTrainersListHelper,
                              MessageProducer messageProducer) {
        this.traineeRepository = traineeRepository;
        this.userService = userService;
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.trainingToTrainerMapper = trainingToTrainerMapper;
        this.getTraineeTrainingsHelper = getTraineeTrainingsHelper;
        this.updateTraineeTrainersListHelper = updateTraineeTrainersListHelper;
        this.messageProducer = messageProducer;
    }


    @Override
    public TraineeProfileDTO getTraineeProfile(String username) {

        log.info("Fetching trainee profile for username: {}", username);
        try {
            Trainee trainee = traineeRepository.findByUserUsername(username);
            if (trainee == null) {
                log.info("Trainee not found for username: {}", username);
                return null;
            }

            User user = trainee.getUser();

            TraineeProfileDTO profileDTO = new TraineeProfileDTO();
            profileDTO.setFirstName(user.getFirstName());
            profileDTO.setLastName(user.getLastName());
            profileDTO.setDateOfBirth(trainee.getDateOfBirth());
            profileDTO.setAddress(trainee.getAddress());
            profileDTO.setActive(user.isActive());

            List<Training> trainings = trainingRepository.findByTraineeId(trainee.getId());
            List<TrainerDTO> trainers = trainings.stream()
                    .map(trainingToTrainerMapper::mapTrainingToTrainerDTO)
                    .toList();

            profileDTO.setTrainers(trainers);

            log.info("Trainee profile fetched successfully for username: {}", username);
            return profileDTO;
        } catch (Exception ex) {
            log.error("Error occurred while fetching trainee profile for username: {}", username, ex);
            throw ex;
        }
    }


    @Override
    @Transactional
    public Trainee updateTraineeProfile(String username, String firstName, String lastName,
                                        Date dateOfBirth, String address, Boolean isActive) {

        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for trainee update. Transaction ID: {}", transactionId);

        try {

            Trainee trainee = traineeRepository.findByUserUsername(username);

            if (trainee != null) {
                User user = trainee.getUser();
                user.setFirstName(firstName != null ? firstName : user.getFirstName());
                user.setLastName(lastName != null ? lastName : user.getLastName());
                trainee.setDateOfBirth(dateOfBirth != null ? dateOfBirth : trainee.getDateOfBirth());
                trainee.setAddress(address != null ? address : trainee.getAddress());
                user.setActive(isActive);
                userService.saveUser(user);
                log.info("Transaction successful for trainee update. Transaction ID: {}", transactionId);

                return traineeRepository.save(trainee);

            } else {
                throw new TraineeNotFoundException("Trainee with username " + username + " not found.");

            }

        } catch (Exception e) {
            log.info("Error occurred while updating trainee. Transaction ID: {}", transactionId, e);
            throw e;
        }
    }


    @Override
    @Transactional
    public Trainee createTrainee(String firstName, String lastName, Date dateOfBirth, String address) throws UsernameValidationException {

        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for trainee creation. Transaction ID: {}", transactionId);

        try {
            User user = userService.createUser(firstName, lastName);

            Trainee trainee = new Trainee();
            trainee.setUser(user);
            trainee.setDateOfBirth(dateOfBirth);
            trainee.setAddress(address);

            log.info("Trainee Created Successfully");
            Trainee createdTrainee = traineeRepository.save(trainee);

            log.info("Trainee created successfully. Transaction ID: {}", transactionId);
            return createdTrainee;
        } catch (Exception e) {
            log.info("Error occurred while creating trainee. Transaction ID: {}", transactionId, e);
            throw e;
        }
    }


    @Override
    @Transactional
    public void updateTraineeStatus(String username, boolean isActive) {
        Trainee trainee = traineeRepository.findByUserUsername(username);
        if (trainee != null) {
            User user = trainee.getUser();
            user.setActive(isActive);
            userService.saveUser(user);
            log.info("Trainee status updated Successfully!");
        } else {
            log.error("Trainee not found with username: " + username);
            throw new IllegalArgumentException("Trainee not found with username: " + username);
        }
    }


    @Override
    @Transactional
    public void deleteTraineeByUsername(String username) {
        String transactionId = UUID.randomUUID().toString();
        log.info("Transaction started for trainee delete. Transaction ID: {}", transactionId);

        Trainee trainee = traineeRepository.findByUserUsername(username);

        TrainingSessionDTO sessionDTO = new TrainingSessionDTO();

        List<Training> trainings = trainingRepository.findByTraineeId(trainee.getId());
        for (Training training : trainings) {
            sessionDTO.setTrainerUsername(training.getTrainer().getUser().getUsername());
            sessionDTO.setFirstName(training.getTrainer().getUser().getFirstName());
            sessionDTO.setLastName(training.getTrainer().getUser().getLastName());
            sessionDTO.setActive(training.getTrainer().getUser().isActive());
            sessionDTO.setTrainingDate(training.getTrainingDate());
            sessionDTO.setTrainingDuration(training.getTrainingDuration());
            sessionDTO.setActionType("DELETE");

            String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();

            messageProducer.sendMessageToTopicWithJwt(token, sessionDTO);

        }



        trainingRepository.deleteAll(trainings);
        log.info("Transaction successful of deleting trainee's associate training. Transaction ID: {}", transactionId);

        userService.deleteUserById(trainee.getUser().getId());
        log.info("Transaction successful of deleting trainee's associate user. Transaction ID: {}", transactionId);
        traineeRepository.delete(trainee);
        log.info("Transaction successful of deleting trainee. Transaction ID: {}", transactionId);

        log.info("Trainee, associated trainings, and user deleted successfully");
    }

    @Override
    public List<TrainingDTO> getTraineeTrainingsList(String username, Date fromDate, Date toDate,
                                                     String trainerName, String trainingTypeName) {


        Trainee trainee = traineeRepository.findByUserUsername(username);
        if (trainee == null) {
            log.error("Trainee not found!");
            return Collections.emptyList();
        }

        Long traineeId = trainee.getId();
        Long trainerId = getTraineeTrainingsHelper.getTrainerId(trainerName);
        Long trainingTypeId = getTraineeTrainingsHelper.getTrainingTypeId(trainingTypeName);

        List<Training> trainings = getTraineeTrainingsHelper.constructQuery(traineeId, fromDate, toDate != null ? DateUtils.addDays(toDate, 1) : null, trainerId, trainingTypeId);

        if (trainerId != null && trainings.isEmpty()) {
            return Collections.singletonList(new TrainingDTO("No trainings found for the specified trainer.", null, null, null, null));
        }

        return getTraineeTrainingsHelper.mapToTrainingDTO(trainings);
    }





    @Override
    @Transactional
    public List<TrainerResponse> updateTraineeTrainersList(String username, List<String> trainerUsernames) throws TraineeNotFoundException, InvalidTrainersException, TrainerNotFoundException {
        Trainee trainee = traineeRepository.findByUserUsername(username);
        if (trainee == null) {
            log.info("Trainee not found with username: {}", username);
            throw new TraineeNotFoundException("Trainee not found with username: " + username);
        }

        Long traineeId = trainee.getId();
        List<Training> trainings = trainingRepository.findByTraineeId(traineeId);

        if (trainerUsernames.size() > trainings.size()) {
            log.warn("Number of trainers provided exceeds the number of available trainings for trainee: {}", username);
            throw new InvalidTrainersException("Number of trainers provided exceeds the number of available trainings");
        }

        Set<Long> updatedTrainingIds = new HashSet<>();

        for (String trainerUsername : trainerUsernames) {
            Trainer trainer = trainerRepository.findByUserUsername(trainerUsername);
            if (trainer == null) {
                log.warn("Trainer not found with username: {}", trainerUsername);
                throw new TrainerNotFoundException("Trainer not found with username: " + trainerUsername);
            }

            Long trainerTrainingTypeId = trainer.getTrainingType().getId();
            boolean trainerAssigned = false;
            for (Training training : trainings) {
                if (!updatedTrainingIds.contains(training.getId())) {
                    Long trainingTrainingTypeId = training.getTrainingType().getId();
                    if (trainingTrainingTypeId.equals(trainerTrainingTypeId)) {
                        training.setTrainer(trainer);
                        trainingRepository.save(training);
                        trainingRepository.flush();
                        updatedTrainingIds.add(training.getId());
                        trainerAssigned = true;
                        break;
                    }
                }
            }

            if (!trainerAssigned) {
                throw new InvalidTrainersException("No available training found for trainer: " + trainerUsername);
            }
        }

        List<TrainerResponse> updatedTrainers = updateTraineeTrainersListHelper.getUpdatedTrainers(trainings, trainerRepository);
        log.info("Updated trainer list retrieved for trainee: {}", username);
        return updatedTrainers;
    }




}
