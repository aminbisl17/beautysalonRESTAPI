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

    @Autowired
    public JwtAuthFilter(
            JwtUtil jwtUtil,
            @Qualifier("adminDetailsService") UserDetailsService adminDetailsService,
            @Qualifier("clientDetailsService") UserDetailsService clientDetailsService
    ) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);  // IMPORTANT!

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails;

                // Decide which service to use based on role in JWT
                if ("ROLE_ADMIN".equals(role)) {
                    userDetails = adminDetailsService.loadUserByUsername(username);
                } else {
                    userDetails = clientDetailsService.loadUserByUsername(username);
                }

                if (jwtUtil.validateToken(token)) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}