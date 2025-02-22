package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record ProductResponseDto(
        Long productId,
        String productName,
        double price,
        CategoryForProductResponseDto categoryForProductResponseDto
) {
}
