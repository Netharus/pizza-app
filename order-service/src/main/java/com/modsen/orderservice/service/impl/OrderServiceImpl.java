package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import com.modsen.orderservice.exception.OrderNotFoundException;
import com.modsen.orderservice.mapper.OrderMapper;
import com.modsen.orderservice.repository.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Order createOrder(OrderCreateDto orderCreateDto) {
        return null;
    }

    @Override
    public PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keywords) {
        return orderMapper.toOrderResponseDtoPage(orderRepository.findAll(pageable, keywords));
    }

    @Override
    public List<OrderResponseDto> findAll() {
        return orderMapper.toOrderResponseDtoList(orderRepository.findAll());
    }

    @Override
    public OrderResponseDto findById(Long id) {
        return orderMapper.toOrderResponseDto(getById(id));
    }

    @Override
    public PageContainerDto<OrderResponseDto> findByUserId(Long userId, Pageable pageable) {
        return orderMapper.toOrderResponseDtoPage(orderRepository.findByUserId(userId, pageable));
    }

    private Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("There is no order with id " + id));
    }
}
