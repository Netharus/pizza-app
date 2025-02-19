package com.modsen.userservice.dto;

public record UsersResponseDto(
        Long id,
        String username,
        String email,
        String fullName,
        String phoneNumber
) {
}
