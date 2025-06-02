package com.danyarov.library.controller;

import com.danyarov.library.model.Book;
import com.danyarov.library.model.Order;
import com.danyarov.library.model.OrderType;
import com.danyarov.library.model.User;
import com.danyarov.library.service.BookService;
import com.danyarov.library.service.OrderService;
import com.danyarov.library.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Book controller
 */
@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private OrderService orderService;

    @Autowired
    public BookController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @GetMapping
    public String listBooks(@RequestParam(required = false) String search,
                            @RequestParam(required = false) String genre,
                            Model model) {
        List<Book> books;

        if (search != null && !search.trim().isEmpty()) {
            books = bookService.search(search);
            model.addAttribute("search", search);
        } else if (genre != null && !genre.trim().isEmpty()) {
            books = bookService.findByGenre(genre);
            model.addAttribute("genre", genre);
        } else {
            books = bookService.findAll();
        }

        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Book> book = bookService.findById(id);
        if (book.isEmpty()) {
            return "redirect:/books";
        }

        model.addAttribute("book", book.get());

        // Check if user has active order for this book
        User user = SessionUtil.getCurrentUser(session);
        if (user != null) {
            boolean hasActiveOrder = orderService.hasActiveOrder(user.getId(), id);
            model.addAttribute("hasActiveOrder", hasActiveOrder);
        }

        return "books/detail";
    }

    @PostMapping("/{id}/request")
    public String requestBook(@PathVariable("id") Long id,
                              @RequestParam OrderType orderType,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        User user = SessionUtil.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            Order order = orderService.createOrder(user.getId(), id, orderType);
            redirectAttributes.addFlashAttribute("success", "msg.book_requested");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/books/" + id;
    }
}
