package com.epam.crmgym.exception;

public class TraineeNotFoundException extends RuntimeException {

    public TraineeNotFoundException(String traineeUsername) {
        super("Trainee not found with username: " + traineeUsername);
    }
}
