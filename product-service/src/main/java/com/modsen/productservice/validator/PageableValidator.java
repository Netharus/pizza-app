package com.modsen.productservice.validator;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PageableValidator implements ConstraintValidator<PageableValid, Pageable> {

    private static final Set<String> allowedFields = AllowedSortFields.getAllowedFields();

    @Override
    public boolean isValid(Pageable p, ConstraintValidatorContext constraintValidatorContext) {
        for (Sort.Order order : p.getSort()) {
            if (!allowedFields.contains(order.getProperty())) {
                constraintValidatorContext.disableDefaultConstraintViolation();


                constraintValidatorContext.buildConstraintViolationWithTemplate(String
                                .format(ErrorMessages.PAGEABLE_VALIDATION_ERROR, order.getProperty()))
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
