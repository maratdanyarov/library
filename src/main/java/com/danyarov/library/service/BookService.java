package com.danyarov.library.service;

import com.danyarov.library.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * Book service interface
 */
public interface BookService {
    /**
     * Find book by ID
     * @param id book ID
     * @return book or empty if not found
     */
    Optional<Book> findById(Long id);

    /**
     * Find all books
     * @return list of all books
     */
    List<Book> findAll();

    /**
     * Search books
     * @param searchTerm search term
     * @return list of matching books
     */
    List<Book> search(String searchTerm);

    /**
     * Find books by genre
     * @param genre book genre
     * @return list of books in specified genre
     */
    List<Book> findByGenre(String genre);

    /**
     * Save book
     * @param book book to save
     * @return saved book
     */
    Book save(Book book);

    /**
     * Update book
     * @param book book to update
     * @return updated book
     */
    Book update(Book book);

    /**
     * Delete book
     * @param id book ID
     * @return true if deleted, false otherwise
     */
    boolean delete(Long id);

    /**
     * Check if book is available
     * @param bookId book ID
     * @return true if available copies > 0
     */
    boolean isAvailable(Long bookId);
}