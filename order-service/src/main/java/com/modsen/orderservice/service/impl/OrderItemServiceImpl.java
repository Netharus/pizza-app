package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.client.ProductClient;
import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.OrderItem;
import com.modsen.orderservice.dto.OrderItemCreateDto;
import com.modsen.orderservice.dto.ProductRequestDto;
import com.modsen.orderservice.dto.ProductResponseForOrderDto;
import com.modsen.orderservice.mapper.OrderItemMapper;
import com.modsen.orderservice.repository.OrderItemRepository;
import com.modsen.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductClient productClient;


    @Override
    @Transactional
    public List<OrderItem> createOrderItems(List<OrderItemCreateDto> orderItemCreateDtos, Order order) {
        ProductResponseForOrderDto productResponseForOrderDto = productClient.getProductData(new ProductRequestDto(getProductIds(orderItemCreateDtos)));

        return orderItemCreateDtos.stream().map(orderItemCreateDto -> {
            OrderItem orderItem = orderItemMapper.fromOrderItemCreateDto(orderItemCreateDto);
            orderItem.setOrder(order);
            orderItem.setPrice(productResponseForOrderDto.dataById().get(orderItem.getProductId()).price());
            orderItem.setProductName(productResponseForOrderDto.dataById().get(orderItem.getProductId()).name());
            return orderItemRepository.save(orderItem);
        }).toList();
    }

    @Override
    public Boolean isProductUsed(Long productId) {
        return orderItemRepository.existsById(productId);
    }

    @Transactional
    protected List<Long> getProductIds(List<OrderItemCreateDto> orderItemCreateDtos) {
        List<Long> productIds = orderItemCreateDtos.stream().map(OrderItemCreateDto::productId).toList();
        return productIds;
    }
}
