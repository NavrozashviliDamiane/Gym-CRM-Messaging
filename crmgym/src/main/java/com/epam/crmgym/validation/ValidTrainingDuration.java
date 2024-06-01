package com.epam.crmgym.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidTrainingDurationValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTrainingDuration {
    String message() default "Invalid training duration. Please provide positive integer between 30 and 500";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
