package com.example.beautysalonRESTAPI.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.beautysalonRESTAPI.dto.TenantInfo;
import com.example.beautysalonRESTAPI.service.TenantService;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final TenantService tenantService;

    public TenantFilter(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String p = request.getRequestURI();

        return p.startsWith("/actuator")
                || p.startsWith("/ws")
                || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String tenantKey = request.getHeader("X-Tenant-ID");

        if (tenantKey == null || tenantKey.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Missing X-Tenant-ID"
            );
            return;
        }

        TenantInfo tenant;

        try {
            tenant = tenantService.findByKey(tenantKey);

        } catch (Exception e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Unknown tenant"
            );
            return;
        }

        try {

            TenantContext.setTenant(tenant.schemaName());
            TenantContext.setTenantId(tenant.id());

            chain.doFilter(request, response);

        } finally {

            TenantContext.clear();
        }
    }
}