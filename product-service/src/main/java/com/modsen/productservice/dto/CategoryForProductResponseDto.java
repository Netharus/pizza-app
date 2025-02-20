package com.modsen.productservice.dto;

import lombok.Builder;

@Builder
public record CategoryForProductResponseDto(
        Long id,
        String name
) {
}
