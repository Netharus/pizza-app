package com.modsen.productservice.dto;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductUpdateDto(
        @NotNull(message = ErrorMessages.PRODUCT_ID_CANT_BE_EMPTY)
        Long id,
        @NotBlank(message = ErrorMessages.PRODUCT_NAME_CANT_BE_EMPTY)
        String name,
        @Positive(message = ErrorMessages.PRICE_CANT_BE_NEGATIVE)
        double price,
        @NotNull(message = ErrorMessages.CATEGORY_ID_CANT_BE_EMPTY)
        Long categoryId
) {
}
