package com.modsen.productservice.dto;

import java.util.List;

public record PageContainerDto<T>(
        List<T> content,
        Integer size,
        Integer pageNum,
        Long totalElements,
        Integer totalPages
) {
}
