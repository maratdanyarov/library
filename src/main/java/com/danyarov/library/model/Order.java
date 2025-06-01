package com.danyarov.library.model;

import java.time.LocalDateTime;

/**
 * Order entity representing book requests
 */
public class Order {
    private Long id;
    private Long userId;
    private Long bookId;
    private Long bookCopyId;
    private OrderType orderType;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Long librarianId;
    private String notes;


    private User user;
    private Book book;
    private BookCopy bookCopy;
    private User librarian;

    // Builder pattern implementation
    public static class Builder {
        private Long id;
        private Long userId;
        private Long bookId;
        private Long bookCopyId;
        private OrderType orderType;
        private OrderStatus status = OrderStatus.PENDING;
        private LocalDateTime orderDate = LocalDateTime.now();
        private LocalDateTime issueDate;
        private LocalDateTime dueDate;
        private LocalDateTime returnDate;
        private Long librarianId;
        private String notes;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder bookId(Long bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder bookCopyId(Long bookCopyId) {
            this.bookCopyId = bookCopyId;
            return this;
        }

        public Builder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder issueDate(LocalDateTime issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder returnDate(LocalDateTime returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder librarianId(Long librarianId) {
            this.librarianId = librarianId;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.id = this.id;
            order.userId = this.userId;
            order.bookId = this.bookId;
            order.bookCopyId = this.bookCopyId;
            order.orderType = this.orderType;
            order.status = this.status;
            order.orderDate = this.orderDate;
            order.issueDate = this.issueDate;
            order.dueDate = this.dueDate;
            order.returnDate = this.returnDate;
            order.librarianId = this.librarianId;
            order.notes = this.notes;
            return order;
        }
    }

    public Order() {}


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getBookCopyId() { return bookCopyId; }
    public void setBookCopyId(Long bookCopyId) { this.bookCopyId = bookCopyId; }

    public OrderType getOrderType() { return orderType; }
    public void setOrderType(OrderType orderType) { this.orderType = orderType; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDateTime issueDate) { this.issueDate = issueDate; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }

    public Long getLibrarianId() { return librarianId; }
    public void setLibrarianId(Long librarianId) { this.librarianId = librarianId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public BookCopy getBookCopy() { return bookCopy; }
    public void setBookCopy(BookCopy bookCopy) { this.bookCopy = bookCopy; }

    public User getLibrarian() { return librarian; }
    public void setLibrarian(User librarian) { this.librarian = librarian; }
}
