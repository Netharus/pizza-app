package com.modsen.orderservice.dto;

import com.modsen.orderservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreateDto(
        @NotBlank(message = ErrorMessages.USER_ID_CANNOT_BE_EMPTY)
        String userId,
        @NotEmpty(message = ErrorMessages.CANNOT_CREATE_WITHOUT_PRODUCTS)
        List<OrderItemCreateDto> orderItemsCreateDtoList
) {
}
