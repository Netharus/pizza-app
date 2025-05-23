package com.modsen.productservice.validator;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PageableValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableValid {
    String message() default ErrorMessages.INVALID_FIELD_NAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
