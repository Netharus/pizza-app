package com.modsen.productservice.dto;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductRequestDto(
        @NotEmpty(message = ErrorMessages.PRODUCT_IDS_LIST_CANT_BE_EMPTY)
        List<Long> productIds
) {
}
