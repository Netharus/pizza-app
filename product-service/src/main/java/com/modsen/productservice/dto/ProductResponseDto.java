package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record ProductResponseDto(
        Long id,
        String name,
        double price,
        CategoryForProductResponseDto categoryForProductResponseDto
) {
}
