package com.modsen.userservice.dto;

import lombok.Builder;

@Builder
public record ProfileResponseDto(
        String username,
        String email,
        String phoneNumber
) {
}
