package com.example.beautysalonRESTAPI.Configuration;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver
        implements CurrentTenantIdentifierResolver<String> {

    private static final String BOOTSTRAP_TENANT = "dbo";

@Override
public String resolveCurrentTenantIdentifier() {
    String tenant = TenantContext.getTenant();
    return tenant != null ? tenant : BOOTSTRAP_TENANT;
}

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}