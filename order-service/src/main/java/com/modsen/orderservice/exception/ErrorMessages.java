package com.modsen.orderservice.exception;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static final String ORDER_ITEM_NOT_FOUND_WITH_PRODUCT_ID = "Order item with product id %d not found";
    public static final String ORDER_NOT_FOUND = "There is no order with id %d";
    public static final String UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String PARSE_ERROR = "Unexpected error: ";
    public static final String PAGEABLE_VALIDATION_ERROR = "There are no fields with this name %s";

}
