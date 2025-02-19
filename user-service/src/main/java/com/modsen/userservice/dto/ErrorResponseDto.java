package com.modsen.userservice.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        HttpStatus httpStatus,
        String message,
        LocalDateTime timestamp
) {
    public ErrorResponseDto(HttpStatus httpStatus, String message) {
        this(httpStatus, message, LocalDateTime.now());
    }
}
