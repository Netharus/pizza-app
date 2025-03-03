package com.modsen.userservice.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum AllowedSortFields {
    ID("id"),
    KEYCLOAK_ID("keycloakId"),
    USERNAME("username"),
    EMAIL("email"),
    FULL_NAME("fullName"),
    PHONE_NUMBER("phoneNumber");

    private final String fieldName;

    public static Set<String> getAllowedFields() {
        return Arrays.stream(values())
                .map(AllowedSortFields::getFieldName)
                .collect(Collectors.toSet());
    }
}
