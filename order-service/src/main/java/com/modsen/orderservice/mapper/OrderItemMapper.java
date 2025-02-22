package com.modsen.orderservice.mapper;

import com.modsen.orderservice.domain.Order;
import com.modsen.orderservice.domain.OrderItem;
import com.modsen.orderservice.dto.OrderCreateDto;
import com.modsen.orderservice.dto.OrderItemCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    OrderItem fromOrderItemCreateDto(OrderItemCreateDto orderItemCreateDto);
}
