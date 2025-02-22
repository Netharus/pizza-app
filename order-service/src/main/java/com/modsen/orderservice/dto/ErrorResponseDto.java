package com.modsen.orderservice.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        HttpStatus httpStatus,
        int statusCode,
        String path,
        String message,
        LocalDateTime timestamp
) {
    public ErrorResponseDto(HttpStatus httpStatus, String message, String path) {
        this(httpStatus, httpStatus.value(), path, message, LocalDateTime.now());
    }

    public ErrorResponseDto(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "", message, LocalDateTime.now());
    }
}
