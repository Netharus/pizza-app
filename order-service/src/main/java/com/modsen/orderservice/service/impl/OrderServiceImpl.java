package com.modsen.orderservice.service.impl;

import com.modsen.orderservice.client.UserClient;
import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.enums.OrderStatus;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import com.modsen.orderservice.exception.ErrorMessages;
import com.modsen.orderservice.exception.InvalidOrderStatusException;
import com.modsen.orderservice.exception.OrderNotFoundException;
import com.modsen.orderservice.mapper.OrderMapper;
import com.modsen.orderservice.repository.OrderRepository;
import com.modsen.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserClient userClient;

    @Override
    @Transactional
    public Order createOrder(OrderCreateDto orderCreateDto) {
        userClient.findById(orderCreateDto.userId());
        Order order = orderMapper.fromOrderCreateDtoToOrder(orderCreateDto);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order createOrder(String userId) {
        userClient.findById(userId);
        return orderRepository.save(Order.builder().userId(userId).build());
    }


    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keywords) {
        return orderMapper.toOrderResponseDtoPage(orderRepository.findAll(pageable, keywords));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAll() {
        return orderMapper.toOrderResponseDtoList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto findById(Long id) {
        return orderMapper.toOrderResponseDto(getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<OrderResponseDto> findByUserId(String userId, Pageable pageable) {
        return orderMapper.toOrderResponseDtoPage(orderRepository.findByUserId(userId, pageable));
    }

    @Override
    public ResponseEntity<Boolean> isProductUsed(Long productId) {
        return ResponseEntity.ok(orderRepository.existsByOrderItems_ProductIdAndStatusNotIn(productId, List.of(OrderStatus.CANCELLED, OrderStatus.DELIVERED)));
    }

    @Override
    public ResponseEntity<Boolean> isUserUsed(String userId) {
        return ResponseEntity.ok(orderRepository.existsByUserIdAndStatusNotIn(userId, List.of(OrderStatus.CANCELLED, OrderStatus.DELIVERED)));
    }

    @Override
    @Transactional(readOnly = true)
    public PageContainerDto<OrderResponseDto> findAll(Pageable pageable, String keyword, String userId) {
        return orderMapper.toOrderResponseDtoPage(orderRepository.findAllByUserId(userId, keyword, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getActualOrders(String userId) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDtoList(orderRepository.findAllByUserIdAndStatusNotIn(userId, List.of(OrderStatus.CANCELLED, OrderStatus.DELIVERED))));
    }

    @Override
    @Transactional
    public OrderResponseDto changeStatus(Long orderId, String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        Order order = getById(orderId);

        isValidStatusTransition(order, orderStatus);

        order.setStatus(orderStatus);

        return orderMapper.toOrderResponseDto(orderRepository.save(order));
    }

    @Transactional
    protected Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(String.format(ErrorMessages.ORDER_NOT_FOUND, id)));
    }

    private void isValidStatusTransition(Order order, OrderStatus newStatus) {
        if (!order.getStatus().canTransitionTo(newStatus)) {
            throw new InvalidOrderStatusException(String.format(ErrorMessages.WRONG_STATUS_ORDER, order.getStatus().getValue(), newStatus.getValue()));
        }
    }
}
