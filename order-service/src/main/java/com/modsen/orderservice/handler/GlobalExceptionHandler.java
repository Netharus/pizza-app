package com.modsen.orderservice.handler;

import com.modsen.orderservice.dto.ErrorResponseDto;
import com.modsen.orderservice.exception.OrderNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

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

}
