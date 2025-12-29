package org.project.todo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.todo.user.UserService;
import org.project.todo.user.dto.UserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private UserService userService;
    private JwtUtil jwtUtil;

    public JwtAuthFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            String id = jwtUtil.extractToken(token);

            if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UUID userId = UUID.fromString(id);
                UserResponse userResponse = userService.getUser(userId);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userResponse, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        filterChain.doFilter(request, response);
    }

}
