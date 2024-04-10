package com.anhvt.trellobe.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DobValidator.class})
public @interface DobConstraint {
    String message() default "Invalid date of birth";
    int min();
    Class<?>[] group() default { };
    Class<? extends Payload>[] payload() default { };
}
