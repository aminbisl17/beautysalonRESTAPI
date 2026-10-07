package com.example.beautysalonRESTAPI.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        try {
            String tenant = resolveAndValidateTenant(request);
            TenantContext.setTenant(tenant);
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
    String tenant = request.getHeader("X-Tenant-ID");
    return "tenant_test".equals(tenant) ? tenant : null;
}

}