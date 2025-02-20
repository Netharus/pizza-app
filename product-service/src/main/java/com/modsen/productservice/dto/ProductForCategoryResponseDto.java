package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record ProductForCategoryResponseDto(
        Long id,
        String name,
        double price
) {
}
