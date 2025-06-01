package com.danyarov.library.model;

/**
 * Order type enumeration
 */
public enum OrderType {
    HOME("HOME"),
    READING_ROOM("READING_ROOM");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderType fromString(String text) {
        for (OrderType t : OrderType.values()) {
            if (t.value.equalsIgnoreCase(text)) {
                return t;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
