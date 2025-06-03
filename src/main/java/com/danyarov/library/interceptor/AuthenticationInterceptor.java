package com.danyarov.library.interceptor;

import com.danyarov.library.model.UserRole;
import com.danyarov.library.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Authentication interceptor to check user login status
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // Check role-based access
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Admin-only paths
        if (path.startsWith("/admin/") && user.getRole() != UserRole.ADMIN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return false;
        }

        // Librarian paths
        if (path.startsWith("/librarian/") &&
                (user.getRole() != UserRole.LIBRARIAN && user.getRole() != UserRole.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return false;
        }

        // Order management paths - allow readers to access their own orders and cancel them
        if (path.startsWith("/orders/") && !path.startsWith("/orders/my")) {
            // Allow cancel action for readers if it's their own order
            if (path.matches("/orders/\\d+/cancel")) {
                // The controller will verify ownership
                return true;
            }

            // For other /orders/ paths, redirect readers to their orders
            if (user.getRole() == UserRole.READER) {
                response.sendRedirect(request.getContextPath() + "/orders/my");
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                modelAndView.addObject("currentUser", user);
            }
        }
    }
}