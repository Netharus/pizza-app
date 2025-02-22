package com.modsen.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderItemResponseDto(
        Long id,
        Long productId,
        int quantity,
        double price
) {
}
