package com.modsen.orderservice.dto;

import java.util.Map;

public record PriceResponseDto(
        Map<Long, Double> priceById
) {
}
