package com.modsen.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsersUpdateDto(
        @NotBlank(message = "username can't be empty")
        String username,
        @Email
        String email,
        @NotBlank(message = "full name can't be empty")
        String fullName,
        @Pattern(
                regexp = "^\\+375(29|33|25)\\d{7}$",
                message = "Phone number must be in the format +375XX0000000, where XX is 29, 33, or 25"
        )
        String phoneNumber
) {
}
