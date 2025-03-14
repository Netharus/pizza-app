package com.modsen.productservice.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AllowedSortFields {
    ID("id"),
    NAME("name"),
    PRICE("price"),
    CATEGORY_NAME("category.name"),
    AVAILABLE("available"),
    CATEGORY_ID("category.id");

    private final String fieldName;

    public static Set<String> getAllowedFields() {
        return Arrays.stream(values())
                .map(AllowedSortFields::getFieldName)
                .collect(Collectors.toSet());
    }
}
