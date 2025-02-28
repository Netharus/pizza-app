package com.modsen.userservice.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        HttpStatus httpStatus,
        int statusCode,
        String path,
        String message,
        String timestamp
) {
    public ErrorResponseDto(HttpStatus httpStatus, String message, String path) {
        this(httpStatus, httpStatus.value(), path, message, LocalDateTime.now().toString());
    }
}
