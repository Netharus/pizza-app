package com.modsen.productservice.dto;

import com.modsen.productservice.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryUpdateDto(
        @NotNull(message = ErrorMessages.CATEGORY_ID_CANT_BE_EMPTY)
        Long id,
        @NotBlank(message = ErrorMessages.CATEGORY_NAME_CANT_BE_EMPTY)
        String name
) {
}
