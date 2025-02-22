package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record PriceRequestDto(
        @NotEmpty(message = " Product ids list can't be empty")
        List<Long> productIds
) {
}
