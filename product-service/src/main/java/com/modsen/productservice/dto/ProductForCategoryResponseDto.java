package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record ProductForCategoryResponseDto(
        Long productId,
        String productName,
        double price,
        boolean available
) {
}
