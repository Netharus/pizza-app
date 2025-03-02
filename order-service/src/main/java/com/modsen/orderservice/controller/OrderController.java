package com.modsen.orderservice.controller;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import com.modsen.orderservice.mapper.OrderMapper;
import com.modsen.orderservice.service.OrderItemService;
import com.modsen.orderservice.service.OrderService;
import com.modsen.orderservice.validator.PageableValid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final OrderMapper orderMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<OrderResponseDto> getOrders(@PageableValid @PageableDefault(sort = "id") Pageable pageable,
                                                        @RequestParam(defaultValue = "") String keyword) {
        return orderService.findAll(pageable, keyword);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrder(@PathVariable Long orderId) {
        return orderService.findById(orderId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PageContainerDto<OrderResponseDto> getOrdersByUserId(@PageableValid @PageableDefault(sort = "id") Pageable pageable,
                                                                @PathVariable String userId) {
        return orderService.findByUserId(userId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto) {
        Order order = orderService.createOrder(orderCreateDto);

        if (!orderCreateDto.orderItemsCreateDtoList().isEmpty()) {
            order.setOrderItems(orderItemService.createOrderItems(orderCreateDto.orderItemsCreateDtoList(), order));
        }

        return orderMapper.toOrderResponseDto(order);
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Boolean> isProductUsed(@PathVariable Long id) {
        return orderService.isProductUsed(id);
    }
}
