package com.modsen.productservice.dto;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductStandaloneCreateDto(
        @NotBlank(message = ErrorMessages.PRODUCT_NAME_CANT_BE_EMPTY)
        String name,
        @NotNull(message = ErrorMessages.PRICE_CANT_BE_NULL)
        @Positive(message = ErrorMessages.PRICE_CANT_BE_NEGATIVE)
        double price,
        Long categoryId
) {
}
