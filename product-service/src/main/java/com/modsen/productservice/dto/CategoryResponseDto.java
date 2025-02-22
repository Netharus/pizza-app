package com.modsen.productservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(
        Long id,
        String name,
        List<ProductForCategoryResponseDto> products
) {
}
