package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record CategoryForProductResponseDto(
        Long categoryId,
        String categoryName
) {
}
