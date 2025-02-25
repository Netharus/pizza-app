package com.modsen.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductRequestDto(
        @NotEmpty(message = " Product ids list can't be empty")
        List<Long> productIds
) {
}
