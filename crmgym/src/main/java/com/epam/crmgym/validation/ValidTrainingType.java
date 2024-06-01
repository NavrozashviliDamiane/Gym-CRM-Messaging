package com.epam.crmgym.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = {TrainingTypeValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTrainingType {
    String message() default "Invalid specialization. Allowed values: CARDIO, WEIGHT_TRAINING, YOGA, CROSSFIT";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

