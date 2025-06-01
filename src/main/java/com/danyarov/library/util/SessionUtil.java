package com.danyarov.library.util;

import com.danyarov.library.model.User;
import jakarta.servlet.http.HttpSession;

/**
 * Session utility class
 */
public class SessionUtil {

    public static final String USER_ATTRIBUTE = "user";
    public static final String CSRF_TOKEN_ATTRIBUTE = "csrfToken";

    /**
     * Get current user from session
     * @param session HTTP session
     * @return current user or null
     */
    public static User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute(USER_ATTRIBUTE);
    }

    /**
     * Set current user in session
     * @param session HTTP session
     * @param user user to set
     */
    public static void setCurrentUser(HttpSession session, User user) {
        session.setAttribute(USER_ATTRIBUTE, user);
    }

    /**
     * Remove current user from session
     * @param session HTTP session
     */
    public static void removeCurrentUser(HttpSession session) {
        session.removeAttribute(USER_ATTRIBUTE);
    }

    /**
     * Check if user is logged in
     * @param session HTTP session
     * @return true if user is logged in
     */
    public static boolean isLoggedIn(HttpSession session) {
        return getCurrentUser(session) != null;
    }

    /**
     * Check if user has role
     * @param session HTTP session
     * @param role role to check
     * @return true if user has the role
     */
    public static boolean hasRole(HttpSession session, String role) {
        User user = getCurrentUser(session);
        return user != null && user.getRole().getValue().equals(role);
    }

    /**
     * Get CSRF token from session
     * @param session HTTP session
     * @return CSRF token
     */
    public static String getCsrfToken(HttpSession session) {
        return (String) session.getAttribute(CSRF_TOKEN_ATTRIBUTE);
    }
}
