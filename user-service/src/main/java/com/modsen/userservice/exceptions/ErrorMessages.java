package com.modsen.userservice.exceptions;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String USER_NOT_FOUND = "User with id %s not found";
    public static final String USER_ALREADY_EXISTS_USERNAME = "User with username %s already exists";
    public static final String USER_ALREADY_EXISTS_EMAIL = "User with email %s already exists";
    public static final String USER_ALREADY_EXISTS_PHONE_NUMBER = "User with phone number %s already exists";
    public static final String PAGEABLE_VALIDATION_ERROR = "There are no fields with this name %s";
    public static final String UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String PARSE_ERROR = "Unexpected error: ";
    public static final String CANNOT_DELETE_USER = "The user with ID %s cannot be deleted because they have active orders";
    public static final String WRONG_ROLE = "Invalid role. Allowed values: USER, ADMIN (case-insensitive).";
    public static final String USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";
    public static final String FULL_NAME_CANNOT_BE_EMPTY = "Full name can't be empty";
    public static final String PHONE_REGEXP = "^\\+375(29|33|25)\\d{7}$";
    public static final String INCORRECT_PHONE_NUMBER = "Phone number must be in the format +375XX0000000, where XX is 29, 33, or 25";
    public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty";
    public static final String PASSWORD_SIZE_ERROR = "Password must be between 8 and 20 characters";
    public static final String PASSWORD_REGEXP = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&)(#^])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String INCORRECT_PASSWORD_FORMAT = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character";
}
