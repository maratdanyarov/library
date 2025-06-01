package com.danyarov.library.model;

import java.time.LocalDateTime;

/**
 * Book copy entity representing individual copies of books
 */
public class BookCopy {
    private Long id;
    private Long bookId;
    private String inventoryNumber;
    private BookCopyStatus status;
    private LocalDateTime createdAt;

    public BookCopy() {}

    public BookCopy(Long id, Long bookId, String inventoryNumber,
                    BookCopyStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.inventoryNumber = inventoryNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getInventoryNumber() { return inventoryNumber; }
    public void setInventoryNumber(String inventoryNumber) { this.inventoryNumber = inventoryNumber; }

    public BookCopyStatus getStatus() { return status; }
    public void setStatus(BookCopyStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
