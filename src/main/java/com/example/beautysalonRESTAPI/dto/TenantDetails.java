package com.example.beautysalonRESTAPI.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TenantDetails {

    private final long id;
    private final String tenantKey;
    private final String schemaName;
    private final String name;
    private final boolean active;
    private final LocalDateTime createdAt;
    private final String domain;

    public TenantDetails(long id, String tenantKey, String schemaName, String name,
                         boolean active, LocalDateTime createdAt, String domain) {
        this.id = id;
        this.tenantKey = tenantKey;
        this.schemaName = schemaName;
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.domain = domain;
    }

    public long getId() {
        return id;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getName() {
        return name;
    }

    // keeps the JSON property named "isActive" (otherwise Jackson would name it "active")
    @JsonProperty("isActive")
    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getDomain() {
        return domain;
    }
}