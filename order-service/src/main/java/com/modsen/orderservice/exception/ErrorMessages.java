package com.modsen.orderservice.exception;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String ORDER_ITEM_NOT_FOUND_WITH_PRODUCT_ID = "Order item with product id %d not found";
    public static final String ORDER_NOT_FOUND = "There is no order with id %d";
    public static final String UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String PARSE_ERROR = "Unexpected error: ";
    public static final String PAGEABLE_VALIDATION_ERROR = "There are no fields with this name %s";
    public static final String WRONG_STATUS = "Invalid status. Allowed values: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED (case-insensitive).";
    public static final String WRONG_STATUS_ORDER = "Invalid status transition from %s to %s";
    public static final String USER_ID_CANNOT_BE_EMPTY = "User id can't be empty";
    public static final String CANNOT_CREATE_WITHOUT_PRODUCTS = "Cannot create order without products";
    public static final String PRODUCT_ID_CANNOT_BE_EMPTY = "Product id can't be empty";
    public static final String QUANTITY_CANNOT_BE_NULL = "Quantity can't be null";
    public static final String QUANTITY_CANNOT_BE_NEGATIVE = "Quantity can't be negative or equal to zero";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
}
