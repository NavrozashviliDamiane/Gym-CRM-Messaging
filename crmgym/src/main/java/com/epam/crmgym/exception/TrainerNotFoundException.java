package com.epam.crmgym.exception;

public class TrainerNotFoundException extends RuntimeException {

    public TrainerNotFoundException(String trainerUsername) {
        super("Trainer not found with username: " + trainerUsername);
    }
}

