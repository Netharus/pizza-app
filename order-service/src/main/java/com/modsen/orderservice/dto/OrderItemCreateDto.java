package com.modsen.orderservice.dto;

import lombok.Builder;

@Builder
public record OrderItemCreateDto(
        Long productId,
        int quantity
) {
}
