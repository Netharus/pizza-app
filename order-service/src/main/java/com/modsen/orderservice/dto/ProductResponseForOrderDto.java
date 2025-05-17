package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ProductResponseForOrderDto(
        @NotEmpty
        Map<Long, ProductData> dataById
) {
    public record ProductData(
            @NotNull
            Double price,
            @NotBlank
            String name
    ) {
    }
}
