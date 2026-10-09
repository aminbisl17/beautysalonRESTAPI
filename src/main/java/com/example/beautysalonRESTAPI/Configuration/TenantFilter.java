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

            System.out.println("TENANT FILTER REACHED");
System.out.println("URI: " + request.getRequestURI());
System.out.println("METHOD: " + request.getMethod());
System.out.println("ORIGIN: " + request.getHeader("Origin"));

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

        String domain = java.net.URI.create(origin).getHost();

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
    System.out.println(tenant.tenantKey());
    chain.doFilter(request, response);

} finally {
    TenantContext.clear();
}
}

}