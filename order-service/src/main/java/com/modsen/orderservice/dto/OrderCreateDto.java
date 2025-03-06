package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderCreateDto(
        @NotBlank(message = "User id can't be empty")
        String userId,
        @NotEmpty(message = "Cannot create order without products")
        List<OrderItemCreateDto> orderItemsCreateDtoList
) {
}
