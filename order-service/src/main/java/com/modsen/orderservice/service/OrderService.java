package com.modsen.orderservice.service;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderCreateDto orderCreateDto);

    Order createOrder(String userId);

    PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keywords);

    List<OrderResponseDto> findAll();

    OrderResponseDto findById(Long id);

    PageContainerDto<OrderResponseDto> findByUserId(String userId, Pageable pageable);

    ResponseEntity<Boolean> isProductUsed(Long productId);

    ResponseEntity<Boolean> isUserUsed(String userId);

    PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keyword, String userId);

    ResponseEntity<?> getActualOrders(String userId);

    OrderResponseDto changeStatus(Long orderId, String status);
}
