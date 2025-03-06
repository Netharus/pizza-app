package com.modsen.userservice.validator;

import com.modsen.userservice.domain.enums.Role;
import com.modsen.userservice.exceptions.ErrorMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private static final Set<String> ALLOWED_ROLES = Arrays.stream(Role.values())
            .map(role -> role.getValue().toUpperCase())
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!(value != null && ALLOWED_ROLES.contains(value.toUpperCase()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessages.WRONG_ROLE).addConstraintViolation();
            return false;
        }
        return true;
    }
}