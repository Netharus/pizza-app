package com.modsen.productservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PageableValidator implements ConstraintValidator<PageableValid, Pageable> {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("id", "name", "price", "category.name", "category.id");

    @Override
    public boolean isValid(Pageable p, ConstraintValidatorContext constraintValidatorContext) {
        for (Sort.Order order : p.getSort()) {
            if (!ALLOWED_SORT_FIELDS.contains(order.getProperty())) {
                return false;
            }
        }
        return true;
    }
}
