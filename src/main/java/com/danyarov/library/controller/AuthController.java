package com.danyarov.library.controller;

import com.danyarov.library.exception.ServiceException;
import com.danyarov.library.exception.ValidationException;
import com.danyarov.library.model.User;
import com.danyarov.library.service.UserService;
import com.danyarov.library.util.SessionUtil;
import com.danyarov.library.util.ValidationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Authentication controller
 */
@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            ValidationUtil.validateEmail(email);
            ValidationUtil.validatePassword(password);

            Optional<User> user = userService.authenticate(email, password);
            if (user.isPresent()) {
                SessionUtil.setCurrentUser(session, user.get());
                logger.info("User logged in: {}", email);

                // Redirect based on role
                switch (user.get().getRole()) {
                    case ADMIN:
                        return "redirect:/admin/users";
                    case LIBRARIAN:
                        return "redirect:/librarian/orders";
                    default:
                        return "redirect:/books";
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "msg.login_failed");
                return "redirect:/login";
            }
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           @RequestParam String confirmPassword,
                           RedirectAttributes redirectAttributes) {
        try {
            // Validate input
            ValidationUtil.validateEmail(user.getEmail());
            ValidationUtil.validatePassword(user.getPassword());
            ValidationUtil.validateRequired(user.getFirstName(), "First name");
            ValidationUtil.validateRequired(user.getLastName(), "Last name");

            if (!user.getPassword().equals(confirmPassword)) {
                throw new ValidationException("confirmPassword", "validation.password.mismatch");
            }

            userService.register(user);
            redirectAttributes.addFlashAttribute("success", "msg.register_success");
            return "redirect:/login";

        } catch (ValidationException | ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtil.removeCurrentUser(session);
        session.invalidate();
        return "redirect:/login";
    }
}
