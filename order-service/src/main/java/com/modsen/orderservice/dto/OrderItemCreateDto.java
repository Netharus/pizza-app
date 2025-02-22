package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record OrderItemCreateDto(
        @NotNull(message = "Product id can't be empty")
        Long productId,
        @NotNull(message = "Quantity can't be null")
        @Positive(message = "Quantity can't be negative or equal to zero")
        int quantity
) {
}
