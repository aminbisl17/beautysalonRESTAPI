package com.example.beautysalonRESTAPI.Configuration;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver
        implements CurrentTenantIdentifierResolver<String> {

    private static final String DEFAULT_TENANT = "dbo";

    @Override
public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContext.getTenant();

    if (tenant == null || tenant.isBlank()) {
        throw new IllegalStateException("No tenant is set for this request");
    }

    return tenant;
}

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}