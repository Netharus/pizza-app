package com.modsen.orderservice.handler;

import com.modsen.orderservice.dto.ErrorResponseDto;
import com.modsen.orderservice.exception.ConflictException;
import com.modsen.orderservice.exception.InvalidOrderStatusException;
import com.modsen.orderservice.exception.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("App error:", ex);

        List<ErrorResponseDto> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(new ErrorResponseDto(HttpStatus.BAD_REQUEST, error.getDefaultMessage(), request.getRequestURI()))
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCategoryNotFoundException(OrderNotFoundException ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleHandlerValidationException(ConstraintViolationException ex, HttpServletRequest request) {
        String errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.BAD_REQUEST, errors, request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidOrderStatusException(InvalidOrderStatusException ex, HttpServletRequest request) {
        log.error("App error:", ex);
        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
}
