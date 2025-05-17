package com.modsen.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderItemResponseDto(
        Long id,
        Long productId,
        String productName,
        int quantity,
        double price
) {
}
