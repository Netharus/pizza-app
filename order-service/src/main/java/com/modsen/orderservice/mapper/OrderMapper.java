package com.modsen.orderservice.mapper;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.OrderItem;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderItemResponseDto;
import com.modsen.orderservice.dto.OrderResponseDto;
import com.modsen.orderservice.dto.PageContainerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    Order fromOrderCreateDtoToOrder(OrderCreateDto orderCreateDto);

    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

    List<OrderResponseDto> toOrderResponseDtoList(List<Order> orders);

    @Mapping(target = "orderItemResponseDtoList", source = "order.orderItems")
    @Mapping(target = "orderStatus", source = "order.status")
    OrderResponseDto toOrderResponseDto(Order order);

    List<OrderItemResponseDto> toOrderItemResponseDtoList(List<OrderItem> orderItems);

    @Mapping(target = "pageNum", source = "number")
    PageContainerDto<OrderResponseDto> toOrderResponseDtoPage(Page<Order> orders);
}
