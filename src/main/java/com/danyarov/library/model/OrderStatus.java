package com.danyarov.library.model;

/**
 * Order status enumeration
 */
public enum OrderStatus {
    PENDING("PENDING"),
    ISSUED("ISSUED"),
    RETURNED("RETURNED"),
    CANCELLED("CANCELLED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus fromString(String text) {
        for (OrderStatus s : OrderStatus.values()) {
            if (s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
