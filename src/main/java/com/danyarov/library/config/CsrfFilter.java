package com.danyarov.library.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * CSRF protection filter
 */
public class CsrfFilter implements Filter {

    private static final String CSRF_TOKEN_ATTR = "csrfToken";
    private static final String CSRF_TOKEN_PARAM = "_csrf";
    private final SecureRandom random = new SecureRandom();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        // Generate token if not exists
        String token = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        if (token == null) {
            token = generateToken();
            session.setAttribute(CSRF_TOKEN_ATTR, token);
        }

        // Make token available to views
        httpRequest.setAttribute(CSRF_TOKEN_ATTR, token);

        // Skip CSRF check for GET requests
        if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // Validate token for POST requests
        String requestToken = httpRequest.getParameter(CSRF_TOKEN_PARAM);
        if (requestToken == null || !requestToken.equals(token)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
            return;
        }

        chain.doFilter(request, response);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}