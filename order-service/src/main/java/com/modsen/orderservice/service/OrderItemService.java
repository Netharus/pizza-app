package com.modsen.orderservice.service;

import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderItemResponseDto;

public interface OrderItemService {

    OrderItemResponseDto createOrderItem(OrderCreateDto orderCreateDto);

}
