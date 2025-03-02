package com.modsen.userservice.exceptions;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String USER_NOT_FOUND = "User with id %s not found";
    public static final String USER_ALREADY_EXISTS_USERNAME = "User with username %s already exists";
    public static final String USER_ALREADY_EXISTS_EMAIL = "User with email %s already exists";
    public static final String USER_ALREADY_EXISTS_PHONE_NUMBER = "User with phone number %s already exists";
}
