package com.modsen.productservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modsen.productservice.dto.ErrorResponseDto;
import com.modsen.productservice.exception.ConflictException;
import com.modsen.productservice.exception.ErrorMessages;
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
            case 409 -> new ConflictException(errorResponse.message());
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
