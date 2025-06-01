package com.danyarov.library.controller;

import com.danyarov.library.model.Order;
import com.danyarov.library.model.OrderStatus;
import com.danyarov.library.model.User;
import com.danyarov.library.service.OrderService;
import com.danyarov.library.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Librarian controller
 */
@Controller
@RequestMapping("/librarian")
public class LibrarianController {
    private OrderService orderService;

    @Autowired
    public LibrarianController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String listOrders(@RequestParam(required = false) String status,
                             Model model) {
        List<Order> orders;

        if (status != null && !status.isEmpty()) {
            orders = orderService.findByStatus(OrderStatus.fromString(status));
        } else {
            orders = orderService.findAllWithDetails();
        }

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        return "librarian/orders";
    }

    @PostMapping("/orders/{id}/issue")
    public String issueOrder(@PathVariable Long id,
                             @RequestParam(defaultValue = "14") int days,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User librarian = SessionUtil.getCurrentUser(session);

        try {
            orderService.issueOrder(id, librarian.getId(), days);
            redirectAttributes.addFlashAttribute("success", "msg.order_issued");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/librarian/orders";
    }

    @PostMapping("/orders/{id}/return")
    public String returnOrder(@PathVariable Long id,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        User librarian = SessionUtil.getCurrentUser(session);

        try {
            orderService.returnOrder(id, librarian.getId());
            redirectAttributes.addFlashAttribute("success", "msg.order_returned");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/librarian/orders";
    }
}
