package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderItemResponseDto;
import com.modsen.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    @Override
    public OrderItemResponseDto createOrderItem(OrderCreateDto orderCreateDto) {
        return null;
    }
}
