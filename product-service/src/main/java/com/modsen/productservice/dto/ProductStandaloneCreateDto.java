package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductStandaloneCreateDto(
        @NotBlank(message = "Product name can't be empty")
        String name,
        @NotNull(message = "Price can't be null")
        @Positive(message = "Price can't be negative or equal to zero")
        double price,
        Long categoryId
) {
}
