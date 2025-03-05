package com.modsen.userservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.modsen.userservice.dto.ErrorResponseDto;
import com.modsen.userservice.exceptions.ErrorMessages;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.NotFoundException;
import org.apache.coyote.BadRequestException;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponseDto errorResponse = parseError(response);

        return switch (response.status()) {
            case 400 -> new BadRequestException(errorResponse.message());
            case 404 -> new NotFoundException(errorResponse.message());
            default -> new RuntimeException(ErrorMessages.UNEXPECTED_ERROR + errorResponse.message());
        };
    }

    private ErrorResponseDto parseError(Response response) {
        try (InputStream body = response.body().asInputStream()) {
            return objectMapper.readValue(body, ErrorResponseDto.class);
        } catch (IOException e) {
            return new ErrorResponseDto(ErrorMessages.PARSE_ERROR);
        }
    }
}
