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
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {

    String tenantId = request.getHeader("X-Tenant-ID");
    String schema = TENANT_SCHEMAS.get(tenantId);

    if (schema == null) {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "Missing or unknown X-Tenant-ID"
        );
        return; // Critical: don't continue the request
    }

    try {
        TenantContext.setTenant(schema);
        chain.doFilter(request, response);
    } finally {
        TenantContext.clear();
    }
}
   // private String resolveAndValidateTenant(HttpServletRequest request) {
        // Look up the tenant from a trusted source, such as the
        // authenticated user's account or a validated hostname.
      //  throw new UnsupportedOperationException("Implement tenant lookup");
    //}

   private String resolveAndValidateTenant(HttpServletRequest request) {
    String tenantId = request.getHeader("X-Tenant-ID");

    if (tenantId == null || !TENANT_SCHEMAS.containsKey(tenantId)) {
        return null;
    }

    return TENANT_SCHEMAS.get(tenantId); // canonical schema name
}
}