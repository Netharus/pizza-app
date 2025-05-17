package com.modsen.orderservice.validator;

import com.modsen.orderservice.domain.enums.OrderStatus;
import com.modsen.orderservice.exception.ErrorMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    private static final Set<String> ALLOWED_STATUSES = Arrays.stream(OrderStatus.values())
            .map(OrderStatus::getValue)
            .collect(Collectors.toSet());


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!(value != null && ALLOWED_STATUSES.contains(value.toUpperCase()))) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(ErrorMessages.WRONG_STATUS).addConstraintViolation();
            return false;
        }
        return true;
    }
}
