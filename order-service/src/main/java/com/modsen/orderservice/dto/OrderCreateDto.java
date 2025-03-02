package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreateDto(
        @NotBlank(message = "User id can't be empty")
        String userId,
        List<OrderItemCreateDto> orderItemsCreateDtoList
) {
}
