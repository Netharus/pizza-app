package com.modsen.orderservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.orderservice.dto.ErrorResponseDto;
import com.modsen.orderservice.exception.ConflictException;
import com.modsen.orderservice.exception.ErrorMessages;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponseDto errorResponse = parseError(response);

        return switch (response.status()) {
            case 400 -> new BadRequestException(errorResponse.message());
            case 404 -> new NotFoundException(errorResponse.message());
            case 409 -> new ConflictException(errorResponse.message());
            default -> new RuntimeException(ErrorMessages.UNEXPECTED_ERROR + errorResponse.message());
        };
    }

    private ErrorResponseDto parseError(Response response) {
        try (InputStream body = response.body().asInputStream()) {
            ErrorResponseDto errorResponse = objectMapper.readValue(body, ErrorResponseDto.class);
            return errorResponse;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ErrorResponseDto(ErrorMessages.PARSE_ERROR);
        }
    }
}
