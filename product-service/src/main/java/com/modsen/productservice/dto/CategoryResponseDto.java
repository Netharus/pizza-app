package com.modsen.productservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponseDto(
        Long categoryId,
        String categoryName,
        List<ProductForCategoryResponseDto> products
) {
}
