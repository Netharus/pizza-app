package com.modsen.orderservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageContainerDto<T>(
        List<T> content,
        Integer size,
        Integer pageNum,
        Long totalElements,
        Integer totalPages
) {
}
