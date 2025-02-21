package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreateDto(
        @NotNull(message = "User id can't be empty")
        Long userId,
        List<OrderItemCreateDto> orderItemsCreateDtoList
) {
}
