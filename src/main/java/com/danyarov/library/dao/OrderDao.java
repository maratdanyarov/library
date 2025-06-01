package com.danyarov.library.dao;

import com.danyarov.library.model.Order;
import com.danyarov.library.model.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Order DAO interface
 */
public interface OrderDao {
    /**
     * Find order by ID
     * @param id order ID
     * @return Optional containing order if found
     */
    Optional<Order> findById(Long id);

    /**
     * Find all orders
     * @return list of all orders
     */
    List<Order> findAll();

    /**
     * Find orders by user ID
     * @param userId user ID
     * @return list of user's orders
     */
    List<Order> findByUserId(Long userId);

    /**
     * Find orders by status
     * @param status order status
     * @return list of orders with specified status
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Find orders by book ID
     * @param bookId book ID
     * @return list of orders for specified book
     */
    List<Order> findByBookId(Long bookId);

    /**
     * Find active order for book by user
     * @param userId user ID
     * @param bookId book ID
     * @return Optional containing active order if found
     */
    Optional<Order> findActiveOrderByUserAndBook(Long userId, Long bookId);

    /**
     * Save new order
     * @param order order to save
     * @return saved order with generated ID
     */
    Order save(Order order);

    /**
     * Update existing order
     * @param order order to update
     * @return updated order
     */
    Order update(Order order);

    /**
     * Delete order by ID
     * @param id order ID
     * @return true if deleted, false otherwise
     */
    boolean deleteById(Long id);

    /**
     * Find orders with full details (including user and book info)
     * @return list of orders with full details
     */
    List<Order> findAllWithDetails();

    /**
     * Find orders by status with full details
     * @param status order status
     * @return list of orders with full details
     */
    List<Order> findByStatusWithDetails(OrderStatus status);
}
