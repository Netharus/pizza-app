package com.modsen.orderservice.dto;

import com.modsen.orderservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record OrderItemCreateDto(
        @NotNull(message = ErrorMessages.PRODUCT_ID_CANNOT_BE_EMPTY)
        Long productId,
        @NotNull(message = ErrorMessages.QUANTITY_CANNOT_BE_NULL)
        @Positive(message = ErrorMessages.QUANTITY_CANNOT_BE_NEGATIVE)
        int quantity
) {
}
