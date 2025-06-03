package com.danyarov.library.dao;

import com.danyarov.library.model.Book;
import com.danyarov.library.model.Page;

import java.util.List;
import java.util.Optional;

/**
 * Book DAO interface
 */
public interface BookDao {
    /**
     * Find book by ID
     * @param id book ID
     * @return Optional containing book if found
     */
    Optional<Book> findById(Long id);

    /**
     * Find all books
     * @return list of all books
     */
    List<Book> findAll();

    Page<Book> findAllPaginated(int pageNumber, int pageSize);

    /**
     * Find books by title (partial match)
     * @param title book title
     * @return list of matching books
     */
    List<Book> findByTitle(String title);

    /**
     * Find books by author (partial match)
     * @param author book author
     * @return list of matching books
     */
    List<Book> findByAuthor(String author);

    /**
     * Find books by genre
     * @param genre book genre
     * @return list of matching books
     */
    List<Book> findByGenre(String genre);


    Page<Book> findByGenrePaginated(String genre, int pageNumber, int pageSize);

    /**
     * Search books by multiple criteria
     * @param searchTerm search term
     * @return list of matching books
     */
    List<Book> search(String searchTerm);

    Page<Book> searchPaginated(String searchTerm, int pageNumber, int pageSize);

    long countAll();

    long countBySearchTerm(String searchTerm);

    long countByGenre(String genre);

    /**
     * Save new book
     * @param book book to save
     * @return saved book with generated ID
     */
    Book save(Book book);

    /**
     * Update existing book
     * @param book book to update
     * @return updated book
     */
    Book update(Book book);

    /**
     * Delete book by ID
     * @param id book ID
     * @return true if deleted, false otherwise
     */
    boolean deleteById(Long id);

    /**
     * Update available copies count
     * @param bookId book ID
     * @param delta change in available copies (positive or negative)
     */
    void updateAvailableCopies(Long bookId, int delta);
}
