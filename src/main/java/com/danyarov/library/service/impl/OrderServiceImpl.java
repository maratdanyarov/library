package com.danyarov.library.service.impl;

import com.danyarov.library.dao.BookDao;
import com.danyarov.library.dao.OrderDao;
import com.danyarov.library.exception.ServiceException;
import com.danyarov.library.model.Book;
import com.danyarov.library.model.Order;
import com.danyarov.library.model.OrderStatus;
import com.danyarov.library.model.OrderType;
import com.danyarov.library.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Order service implementation
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderDao orderDao;
    private BookDao bookDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, BookDao bookDao) {
        this.orderDao = orderDao;
        this.bookDao = bookDao;
    }

    @Override
    public Order createOrder(Long userId, Long bookId, OrderType orderType) {
        // Check if book is available
        Optional<Book> book = bookDao.findById(bookId);
        if (book.isEmpty()) throw new ServiceException("Book not found with id: " + bookId);

        if (book.get().getAvailableCopies() <= 0) {
            throw new ServiceException("No copies available for book: " + book.get().getTitle());
        }

        // Check if user already has active order for this book
        if (hasActiveOrder(userId, bookId)) {
            throw new ServiceException("User already has an active order for this book");
        }

        Order order = new Order.Builder()
                .userId(userId)
                .bookId(bookId)
                .orderType(orderType)
                .status(OrderStatus.PENDING)
                .build();

        logger.info("Creating order for user {} and book {}", userId, bookId);
        return orderDao.save(order);
    }

    @Override
    public Order issueOrder(Long orderId, Long librarianId, int lendingDays) {
        Optional<Order> orderOpt = orderDao.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ServiceException("Order not found with id: " + orderId);
        }

        Order order = orderOpt.get();
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ServiceException("Order is not in PENDING status");
        }

        // Update order
        order.setStatus(OrderStatus.ISSUED);
        order.setLibrarianId(librarianId);
        order.setIssueDate(LocalDateTime.now());
        order.setDueDate(LocalDateTime.now().plusDays(lendingDays));

        // Update available copies
        bookDao.updateAvailableCopies(order.getBookId(), -1);

        logger.info("Issuing order {} by librarian {}", orderId, librarianId);
        return orderDao.update(order);
    }

    @Override
    public Order returnOrder(Long orderId, Long librarianId) {
        Optional<Order> orderOpt = orderDao.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ServiceException("Order not found with id: " + orderId);
        }

        Order order = orderOpt.get();
        if (order.getStatus() != OrderStatus.ISSUED) {
            throw new ServiceException("Order is not in ISSUED status");
        }

        // Update order
        order.setStatus(OrderStatus.RETURNED);
        order.setReturnDate(LocalDateTime.now());
        order.setLibrarianId(librarianId);

        // Update available copies
        bookDao.updateAvailableCopies(order.getBookId(), 1);

        logger.info("Returning order {} by librarian {}", orderId, librarianId);
        return orderDao.update(order);
    }

    @Override
    public Order cancelOrder(Long orderId, Long userId) {
        Optional<Order> orderOpt = orderDao.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ServiceException("Order not found with id: " + orderId);
        }

        Order order = orderOpt.get();

        // Verify user owns the order
        if (!order.getUserId().equals(userId)) {
            throw new ServiceException("User is not authorized to cancel this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ServiceException("Only pending orders can be cancelled");
        }

        // Update order
        order.setStatus(OrderStatus.CANCELLED);

        logger.info("Cancelling order {} by user {}", orderId, userId);
        return orderDao.update(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findByUser(Long userId) {
        return orderDao.findByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderDao.findByStatus(status);
    }

    @Override
    public List<Order> findAllWithDetails() {
        return orderDao.findAllWithDetails();
    }

    @Override
    public List<Order> findPendingOrdersWithDetails() {
        return orderDao.findByStatusWithDetails(OrderStatus.PENDING);
    }

    @Override
    public boolean hasActiveOrder(Long userId, Long bookId) {
        return orderDao.findActiveOrderByUserAndBook(userId, bookId).isPresent();
    }
}
