package com.epam.crmgym.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredBooleanValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredBoolean {
    String message() default "Field is required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

