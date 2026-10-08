package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.model.Tenants;

public interface TenantsRepository extends JpaRepository<Tenants, Long> {
        Optional<Tenants> findByTenantKey(String tenantKey);
}
