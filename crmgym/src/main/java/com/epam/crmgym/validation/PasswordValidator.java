package com.epam.crmgym.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.length() < 8) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must have at least 8 characters").addConstraintViolation();
            return false;
        }

        if (!value.matches("[a-zA-Z0-9!@#$%^&*()_+=-]+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password can only contain letters, numbers, and special characters").addConstraintViolation();
            return false;
        }

        if (value.contains(" ")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password cannot contain empty spaces").addConstraintViolation();
            return false;
        }

        return true;
    }
}

