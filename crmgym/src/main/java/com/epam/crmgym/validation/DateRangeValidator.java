package com.epam.crmgym.validation;


import com.epam.crmgym.dto.trainee.TraineeTrainingsRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, TraineeTrainingsRequestDTO> {

    @Override
    public boolean isValid(TraineeTrainingsRequestDTO request, ConstraintValidatorContext context) {
        if (request == null || request.getFromDate() == null || request.getToDate() == null) {
            return true;
        }
        return !request.getFromDate().after(request.getToDate());
    }
}

