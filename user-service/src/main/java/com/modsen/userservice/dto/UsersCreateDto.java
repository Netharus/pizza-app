package com.modsen.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UsersCreateDto(
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
        String phoneNumber,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&)(#^])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password
) {
}
