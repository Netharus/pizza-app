package com.modsen.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProductRequestDto(
        List<Long> productIds
) {
}
