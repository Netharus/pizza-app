package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import com.modsen.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(OrderCreateDto orderCreateDto) {
        return null;
    }

    @Override
    public PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keywords) {
        return null;
    }

    @Override
    public List<OrderResponseDto> findAll() {
        return List.of();
    }

    @Override
    public OrderResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageContainerDto<OrderResponseDto> findByUserId(Long userId) {
        return null;
    }
}
