package com.modsen.orderservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record UserCreateOrderDto(
        @NotEmpty(message = "Cannot create order without products")
        List<OrderItemCreateDto> orderItemsCreateDtoList) {
}
