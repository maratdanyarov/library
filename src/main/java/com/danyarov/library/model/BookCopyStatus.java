package com.danyarov.library.model;

/**
 * Book copy status enumeration
 */
public enum BookCopyStatus {
    AVAILABLE("AVAILABLE"),
    ISSUED("ISSUED"),
    RESERVED("RESERVED");

    private final String value;

    BookCopyStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BookCopyStatus fromString(String text) {
        for (BookCopyStatus s : BookCopyStatus.values()) {
            if (s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
