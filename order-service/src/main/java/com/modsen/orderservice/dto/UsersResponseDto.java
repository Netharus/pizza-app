package com.modsen.orderservice.dto;

public record UsersResponseDto(
        Long id,
        String username,
        String keycloakId,
        String email,
        String fullName,
        String phoneNumber
) {
}
