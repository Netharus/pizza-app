package com.modsen.orderservice.service;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.OrderItem;
import com.modsen.orderservice.dto.OrderItemCreateDto;
import com.modsen.orderservice.dto.ProductResponseForOrderDto;

import java.util.List;

public interface OrderItemService {

    List<OrderItem> createOrderItems(List<OrderItemCreateDto> orderItemCreateDtos, Order order);

    Boolean isProductUsed(Long productId);

    void updateOrderItemData(ProductResponseForOrderDto productResponseForOrderDto);
}
