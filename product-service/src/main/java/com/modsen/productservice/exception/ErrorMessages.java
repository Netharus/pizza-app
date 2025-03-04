package com.modsen.productservice.exception;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public static final String CATEGORY_ALREADY_EXIST = "Category with the name %s already exists";
    public static final String CATEGORY_NOT_FOUND = "Category with id %d not found";
    public static final String PRODUCT_UNAVAILABLE_TO_DELETE_CAT = "Product with this ids are not available to delete. Ids: %s";
    public static final String PRODUCT_ALREADY_EXIST = "Product with the name %s already exists";
    public static final String PRODUCT_NOT_FOUND = "Product with id %d not found";
    public static final String PRODUCT_UNAVAILABLE_TO_DELETE = "Product with this id %d already used in order. Product available change to false, wait until last order with this product ends";
    public static final String PRODUCT_UNAVAILABLE = "Product with id %d is no longer available";
    public static final String UNEXPECTED_ERROR = "Unexpected error: ";
    public static final String PARSE_ERROR = "Unexpected error: ";
    public static final String PAGEABLE_VALIDATION_ERROR = "There are no fields with this name %s";

}
