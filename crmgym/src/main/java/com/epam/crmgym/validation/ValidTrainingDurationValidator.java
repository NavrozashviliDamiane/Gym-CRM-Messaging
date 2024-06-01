package com.epam.crmgym.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTrainingDurationValidator implements ConstraintValidator<ValidTrainingDuration, Integer> {

    @Override
    public void initialize(ValidTrainingDuration constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value >= 30 && value <= 500;
    }
}
