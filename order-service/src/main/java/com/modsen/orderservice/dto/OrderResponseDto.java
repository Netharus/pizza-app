package com.modsen.orderservice.dto;

import com.modsen.orderservice.domain.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDto(
        Long id,
        Long userId,
        LocalDateTime orderDate,
        OrderStatus orderStatus,
        List<OrderItemResponseDto> orderItemResponseDtoList
) {
}
