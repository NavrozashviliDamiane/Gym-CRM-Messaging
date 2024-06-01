package com.epam.crmgym.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class TrainingTypeValidator implements ConstraintValidator<ValidTrainingType, String> {

    private final Set<String> allowedValues = new HashSet<>(Arrays.asList("CARDIO", "WEIGHT_TRAINING", "YOGA", "CROSSFIT"));

    @Override
    public void initialize(ValidTrainingType constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return allowedValues.contains(value.toUpperCase());
    }
}

