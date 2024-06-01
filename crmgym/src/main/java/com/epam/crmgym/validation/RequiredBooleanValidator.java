package com.epam.crmgym.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredBooleanValidator implements ConstraintValidator<RequiredBoolean, Boolean> {
    @Override
    public void initialize(RequiredBoolean constraintAnnotation) {
    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value != null;
    }
}

