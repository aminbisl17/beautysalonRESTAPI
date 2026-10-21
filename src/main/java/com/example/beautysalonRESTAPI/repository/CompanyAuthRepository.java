package com.example.beautysalonRESTAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.beautysalonRESTAPI.model.CompanyAuth;

public interface CompanyAuthRepository extends JpaRepository<CompanyAuth, Long> {

        Optional<CompanyAuth> findByUsername(String username);
    Optional<CompanyAuth> findByTenantIdAndUsername(
            Long tenantId,
            String username
    );

    boolean existsByTenantId(Long tenantId);

    boolean existsByUsername(String username);
}
