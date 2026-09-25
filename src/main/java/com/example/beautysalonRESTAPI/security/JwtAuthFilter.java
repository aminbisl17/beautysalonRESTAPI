package com.example.beautysalonRESTAPI.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService adminDetailsService;
    private final UserDetailsService clientDetailsService;
    private final UserDetailsService employeeDetailsService;

    public JwtAuthFilter(
            JwtUtil jwtUtil,
            @Qualifier("adminDetailsService") UserDetailsService adminDetailsService,
            @Qualifier("clientDetailsService") UserDetailsService clientDetailsService,
            @Qualifier("employeeDetailsService") UserDetailsService employeeDetailsService) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.employeeDetailsService = employeeDetailsService;
    }

    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    if (request.getRequestURI().startsWith("/ws")) {
        filterChain.doFilter(request, response);
        return;
    }

    try {
        String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

String username = jwtUtil.extractUsername(token);
String role = jwtUtil.extractRole(token);
String type = jwtUtil.extractType(token);


if (jwtUtil.validateToken(token) && "COMPANY_ACCESS".equals(type)) {


    UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority("SCOPE_COMPANY"))
            );

    SecurityContextHolder.getContext().setAuthentication(auth);


    filterChain.doFilter(request, response);


    return;
}

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
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {

                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid JWT token");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write("Invalid JWT token");
    return;
}
        filterChain.doFilter(request, response);
    }
}