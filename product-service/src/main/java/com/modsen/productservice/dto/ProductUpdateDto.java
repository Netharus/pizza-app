package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record ProductUpdateDto(
        @NotNull(message = "Category id can't be null")
        Long id,
        @NotBlank(message = "Category name can't be empty")
        String name,
        @Positive(message = "Price can't be negative or equal to zero")
        double price,
        Long categoryId
) {
}
