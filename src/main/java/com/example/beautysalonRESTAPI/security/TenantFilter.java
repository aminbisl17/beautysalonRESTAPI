package com.example.beautysalonRESTAPI.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.beautysalonRESTAPI.Configuration.TenantContext;
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
                || p.startsWith("/tenants") ||
p.startsWith("/whatsapp")
                || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

 
    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain)
        throws ServletException, IOException {

String tenantKey = request.getHeader("X-Tenant-ID");

TenantInfo tenant;

try {
    if (tenantKey != null && !tenantKey.isBlank()) {

        tenant = tenantService.findByKey(tenantKey);

    } else {

       String origin = request.getHeader("Origin");

if (origin == null || origin.isBlank()) {
    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Missing tenant information"
    );
    return;
}

String host = java.net.URI.create(origin).getHost();

System.out.println(host);
if (host == null) {
    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid origin"
    );
    return;
}

String[] parts = host.split("\\.");

if (parts.length < 2) {
    response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Invalid domain"
    );
    return;
}

String domain = parts[parts.length - 2];

System.out.println(domain);
tenant = tenantService.findByDomain(domain);

    }

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
    TenantContext.setTenantKey(tenant.tenantKey());

    chain.doFilter(request, response);

} finally {
    TenantContext.clear();
}
}

}