package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryCreateDto(
        @NotBlank(message = "Category name can't be empty")
        String name,
        List<ProductCreateDto> products
) {
}
