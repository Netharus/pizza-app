package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.client.ProductClient;
import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.OrderItem;
import com.modsen.orderservice.domain.enums.OrderStatus;
import com.modsen.orderservice.dto.OrderItemCreateDto;
import com.modsen.orderservice.dto.ProductRequestDto;
import com.modsen.orderservice.dto.ProductResponseForOrderDto;
import com.modsen.orderservice.exception.ErrorMessages;
import com.modsen.orderservice.exception.OrderItemNotFound;
import com.modsen.orderservice.mapper.OrderItemMapper;
import com.modsen.orderservice.repository.OrderItemRepository;
import com.modsen.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void updateOrderItemData(ProductResponseForOrderDto productResponseForOrderDto) {
        productResponseForOrderDto.dataById().forEach((productId, productData) -> {
            OrderItem orderItem = getOrderItemByProductId(productId);
            if (orderItem.getOrder().getStatus() == OrderStatus.PENDING) {
                orderItem.setPrice(productResponseForOrderDto.dataById().get(productId).price());
                orderItem.setProductName(productResponseForOrderDto.dataById().get(productId).name());
                orderItemRepository.save(orderItem);
            }
        });
    }

    @Transactional
    protected List<Long> getProductIds(List<OrderItemCreateDto> orderItemCreateDtos) {
        List<Long> productIds = orderItemCreateDtos.stream().map(OrderItemCreateDto::productId).toList();
        return productIds;
    }

    @Transactional
    protected OrderItem getOrderItemByProductId(Long productId) {
        return orderItemRepository
                .findByProductId(productId)
                .orElseThrow(() -> new OrderItemNotFound(String
                        .format(ErrorMessages.ORDER_ITEM_NOT_FOUND_WITH_PRODUCT_ID, productId)));
    }
}
