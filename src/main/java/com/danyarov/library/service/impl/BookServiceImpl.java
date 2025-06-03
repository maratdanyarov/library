package com.danyarov.library.service.impl;

import com.danyarov.library.dao.BookDao;
import com.danyarov.library.exception.ServiceException;
import com.danyarov.library.model.Book;
import com.danyarov.library.model.Page;
import com.danyarov.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Book service implementation
 */
@Service
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private static final int DEFAULT_PAGE_SIZE = 12;

    private BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Page<Book> findAllPaginated(int pageNumber, int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        return bookDao.findAllPaginated(pageNumber, pageSize);
    }

    @Override
    public List<Book> search(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAll();
        }
        return bookDao.search(searchTerm);
    }

    @Override
    public Page<Book> searchPaginated(String searchTerm, int pageNumber, int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAllPaginated(pageNumber, pageSize);
        }
        return bookDao.searchPaginated(searchTerm, pageNumber, pageSize);
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return bookDao.findByGenre(genre);
    }

    @Override
    public Page<Book> findByGenrePaginated(String genre, int pageNumber, int pageSize) {
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        return bookDao.findByGenrePaginated(genre, pageNumber, pageSize);
    }

    @Override
    public Book save(Book book) {
        if (book.getTotalCopies() == null) {
            book.setTotalCopies(1);
        }
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies());
        }

        logger.info("Saving new book: {}", book.getTitle());
        return bookDao.save(book);
    }

    @Override
    public Book update(Book book) {
        Optional<Book> existingBook = bookDao.findById(book.getId());
        if (existingBook.isEmpty()) {
            throw new ServiceException("Book not found with id: " + book.getId());
        }

        logger.info("Updating book: {}", book.getTitle());
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Deleting book with id: {}", id);
        return bookDao.deleteById(id);
    }

    @Override
    public boolean isAvailable(Long bookId) {
        Optional<Book> book = bookDao.findById(bookId);
        return book.map(b -> b.getAvailableCopies() > 0).orElse(false);
    }
}