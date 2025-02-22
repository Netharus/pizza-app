package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductUpdateDto(
        @NotNull(message = "Category id can't be null")
        Long id,
        @NotBlank(message = "Category name can't be empty")
        String name
) {
}
