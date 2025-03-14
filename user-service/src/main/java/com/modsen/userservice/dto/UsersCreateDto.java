package com.modsen.userservice.dto;

import com.modsen.userservice.exceptions.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UsersCreateDto(
        @NotBlank(message = ErrorMessages.USERNAME_CANNOT_BE_EMPTY)
        String username,
        @Email
        String email,
        @NotBlank(message = ErrorMessages.FULL_NAME_CANNOT_BE_EMPTY)
        String fullName,
        @Pattern(
                regexp = ErrorMessages.PHONE_REGEXP,
                message = ErrorMessages.INCORRECT_PHONE_NUMBER
        )
        String phoneNumber,

        @NotBlank(message = ErrorMessages.PASSWORD_CANNOT_BE_EMPTY)
        @Size(min = 8, max = 20, message = ErrorMessages.PASSWORD_SIZE_ERROR)
        @Pattern(
                regexp = ErrorMessages.PASSWORD_REGEXP,
                message = ErrorMessages.INCORRECT_PASSWORD_FORMAT
        )
        String password
) {
}
