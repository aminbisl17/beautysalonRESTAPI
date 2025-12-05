package com.example.beautysalonRESTAPI.backend.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService adminDetailsService;
    private final UserDetailsService clientDetailsService;
    private final UserDetailsService employeeDetailsService;

    @Autowired
    public JwtAuthFilter(
            JwtUtil jwtUtil,
            @Qualifier("adminDetailsService") UserDetailsService adminDetailsService,
            @Qualifier("clientDetailsService") UserDetailsService clientDetailsService,
            @Qualifier("employeeDetailsService") UserDetailsService employeeDetailsService
    ) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.employeeDetailsService = employeeDetailsService;
    }
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws ServletException, IOException {

    try {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails;

                if ("ROLE_ADMIN".equals(role)) {
                    userDetails = adminDetailsService.loadUserByUsername(username);
                } else if ("ROLE_CLIENT".equals(role)) {
                    userDetails = clientDetailsService.loadUserByUsername(username);
                } else if ("ROLE_EMPLOYEE".equals(role)) {
                    userDetails = employeeDetailsService.loadUserByUsername(username);
                } else {
                    userDetails = null;
                }

                if (userDetails != null && jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    // Invalid token → respond 401 immediately
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid JWT token");
                    return;  // stop filter chain
                }
            }
        }
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Invalid JWT token");
        return; // stop filter chain
    }

    // Continue the chain for valid requests
    filterChain.doFilter(request, response);
}
}