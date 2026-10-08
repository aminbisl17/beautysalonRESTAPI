package com.example.beautysalonRESTAPI.Configuration;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT =
            new ThreadLocal<>();

    private static final ThreadLocal<Long> CURRENT_TENANT_ID =
            new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static String getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setTenantId(Long tenantId) {
        CURRENT_TENANT_ID.set(tenantId);
    }

    public static Long getTenantId() {
        return CURRENT_TENANT_ID.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
        CURRENT_TENANT_ID.remove();
    }
}