package com.gautam.ecommerce_backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ DEBUG: to confirm filter is running
        System.out.println("JwtFilter is running...");

        String authHeader = request.getHeader("Authorization");
        String path = request.getRequestURI();

        // 🔥 CHANGE 1: Added METHOD CHECK (VERY IMPORTANT)
        // WHY: Earlier you allowed ALL /api/users requests (including GET)
        // NOW: Only allow POST /api/users (register) and login API

        if (path.equals("/api/users/login") || 
           (path.equals("/api/users") && request.getMethod().equals("POST"))) {

            // ✅ Public APIs → allow request
            filterChain.doFilter(request, response);
            return;
        }

        // 🔥 CHANGE 2: Enforce token presence
        // WHY: Earlier, if token was missing → request still passed
        // NOW: Block request if no token

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Missing or invalid token\"}");
            return;
        }

        // Extract token
        String token = authHeader.substring(7);

        // 🔥 CHANGE 3: Enforce token validity
        // WHY: Invalid tokens should NOT be allowed

        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Invalid token\"}");
            return;
        }

        // ✅ Valid token → extract user info
        String email = jwtUtil.extractEmail(token);
        System.out.println("Authenticated user: " + email);

        // Continue request
        filterChain.doFilter(request, response);
    }
}