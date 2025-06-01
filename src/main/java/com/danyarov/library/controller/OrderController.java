package com.danyarov.library.controller;

import com.danyarov.library.model.Order;
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
 * Order controller
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my")
    public String myOrders(HttpSession session, Model model) {
        User user = SessionUtil.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.findByUser(user.getId());
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @PostMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        User user = SessionUtil.getCurrentUser(session);
        if (user == null) {
            return "redirect:/login";
        }

        try {
            orderService.cancelOrder(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "msg.order_cancelled");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/orders/my";
    }
}
