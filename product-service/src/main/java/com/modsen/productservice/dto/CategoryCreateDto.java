package com.modsen.productservice.dto;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryCreateDto(
        @NotBlank(message = ErrorMessages.CATEGORY_NAME_CANT_BE_EMPTY)
        String name,
        List<ProductCreateDto> products
) {
}
