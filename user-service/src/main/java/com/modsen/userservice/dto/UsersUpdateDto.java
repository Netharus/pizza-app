package com.modsen.userservice.dto;

import com.modsen.userservice.exceptions.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsersUpdateDto(
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
        String phoneNumber
) {
}
