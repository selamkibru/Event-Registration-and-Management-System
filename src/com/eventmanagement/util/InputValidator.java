package com.eventmanagement.util;

public class InputValidator {

    public static void validateNonEmpty(String field, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty");
        }
    }

    public static void validatePositive(String field, int value) {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }
}
