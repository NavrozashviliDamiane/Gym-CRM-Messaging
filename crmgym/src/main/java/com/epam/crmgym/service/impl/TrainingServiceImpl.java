package com.epam.crmgym.service.impl;

import com.epam.crmgym.config.activemq.MessageProducer;
import com.epam.crmgym.dto.client.TrainingSessionDTO;
import com.epam.crmgym.util.CircuitBreakerStateLogger;
import com.epam.crmgym.util.transaction.TransactionIdGenerator;
import lombok.extern.slf4j.Slf4j;
import com.epam.crmgym.entity.*;
import com.epam.crmgym.repository.TraineeRepository;
import com.epam.crmgym.repository.TrainerRepository;
import com.epam.crmgym.repository.TrainingRepository;
import com.epam.crmgym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final MessageProducer messageProducer;


    private final CircuitBreakerStateLogger circuitBreakerStateLogger;




    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository, MessageProducer messageProducer, CircuitBreakerStateLogger circuitBreakerStateLogger) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.messageProducer = messageProducer;
        this.circuitBreakerStateLogger = circuitBreakerStateLogger;

    }



    @Override
    @Transactional
    public Training createTraining(String username, String trainerUsername,
                                   String trainingName, Date trainingDate, Integer trainingDuration) {

        String transactionId = TransactionIdGenerator.generateTransactionId();
        log.info("Transaction ID: {} - Creating training for username: {}, trainerUsername: {}, trainingName: {}, trainingDate: {}, trainingDuration: {}",
                transactionId, username, trainerUsername, trainingName, trainingDate, trainingDuration);

        circuitBreakerStateLogger.logCircuitBreakerState();

        Trainee trainee = traineeRepository.findByUserUsername(username);
        Trainer trainer = trainerRepository.findByUserUsername(trainerUsername);
        TrainingType trainingType = trainer.getTrainingType();

        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingName(trainingName);
        training.setTrainingDate(trainingDate);
        training.setTrainingDuration(trainingDuration);
        training.setTrainingType(trainingType);

        TrainingSessionDTO sessionDTO = new TrainingSessionDTO(trainerUsername, trainer.getUser().getFirstName(),
                trainer.getUser().getLastName(), trainer.getUser().isActive(),
                trainingDate, trainingDuration, "ADD");

        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();


        messageProducer.sendMessageToTopicWithJwt(token, sessionDTO);



        log.info("Transaction ID: {} - Sending training session to TrainerWorkloadClient: {}", transactionId, sessionDTO);


        log.info("Transaction ID: {} - ", transactionId);
        return trainingRepository.save(training);
    }

}
