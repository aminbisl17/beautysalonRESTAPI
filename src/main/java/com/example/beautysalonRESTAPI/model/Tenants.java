package com.example.beautysalonRESTAPI.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tenants", schema = "dbo")
public class Tenants {

    @Id
    private Long id;

    private String tenantKey;
    private String schemaName;
    private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenantKey() {
		return tenantKey;
	}
	public void setTenantKey(String tenantKey) {
		this.tenantKey = tenantKey;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    // getters/setters
}