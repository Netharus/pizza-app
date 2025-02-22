package com.modsen.productservice.dto;

import java.util.Map;

public record PriceResponseDto(
        Map<Long, Double> priceById
) {
}
