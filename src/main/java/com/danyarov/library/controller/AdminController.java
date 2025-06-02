package com.danyarov.library.controller;

import com.danyarov.library.exception.ServiceException;
import com.danyarov.library.exception.ValidationException;
import com.danyarov.library.model.Book;
import com.danyarov.library.model.UserRole;
import com.danyarov.library.model.User;
import com.danyarov.library.service.BookService;
import com.danyarov.library.service.UserService;
import com.danyarov.library.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Admin controller
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private BookService bookService;

    @Autowired
    public AdminController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            userService.toggleActiveStatus(id);
            redirectAttributes.addFlashAttribute("success", "User status updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("newBook", new Book());
        return "admin/books";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute Book book,
                          RedirectAttributes redirectAttributes) {
        try {
            ValidationUtil.validateRequired(book.getTitle(), "Title");
            ValidationUtil.validateRequired(book.getAuthor(), "Author");
            ValidationUtil.validatePositive(book.getTotalCopies(), "Total copies");

            bookService.save(book);
            redirectAttributes.addFlashAttribute("success", "Book added successfully");
        } catch (ValidationException | ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("newBook", book);
        }
        return "redirect:/admin/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable("id") Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Book deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/books";
    }
}
