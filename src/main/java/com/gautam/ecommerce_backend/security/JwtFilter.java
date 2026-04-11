package com.gautam.ecommerce_backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain)
            throws ServletException, IOException {


       //System.out.println("JwtFilter is running...");

        String authHeader = request.getHeader("Authorization");
//        String path = request.getRequestURI();
//
//
//        if (path.equals("/api/users/login") || 
//           (path.equals("/api/users") && request.getMethod().equals("POST"))) {
//
//
//            filterChain.doFilter(request, response);
//            return;
//        }

//
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//        	
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Missing or invalid token\"}");
//            return;
//        }

        String token = authHeader.substring(7);

//
//        if (!jwtUtil.isTokenValid(token)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.setContentType("application/json");
//            response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Invalid token\"}");
//            return;
//        }
        try {
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authenticated user: " + email);
            }
        } catch (Exception e) {
        }
        }
    filterChain.doFilter(request, response);
}
//
//        String email = jwtUtil.extractEmail(token);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//        
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        //System.out.println("Authenticated: " + 
//         	   //SecurityContextHolder.getContext().getAuthentication());
//        System.out.println("Authenticated user: " + email);
//
//
//        filterChain.doFilter(request, response);
//    }
}