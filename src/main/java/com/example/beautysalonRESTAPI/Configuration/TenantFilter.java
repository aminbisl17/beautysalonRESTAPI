package com.example.beautysalonRESTAPI.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final Map<String, String> TENANT_SCHEMAS = Map.of(
        "tenant-test", "tenant_test",
        "tenant-dbo", "dbo"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String p = request.getRequestURI();
        return p.startsWith("/actuator") || p.startsWith("/ws")
            || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String tenantId = request.getHeader("X-Tenant-ID");
        String schema = (tenantId == null) ? null : TENANT_SCHEMAS.get(tenantId);

        if (schema == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Missing or unknown X-Tenant-ID");
            return;
        }
        try {
            TenantContext.setTenant(schema);
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}