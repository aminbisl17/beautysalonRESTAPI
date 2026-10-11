package com.example.beautysalonRESTAPI.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.service.TenantService;
import com.example.beautysalonRESTAPI.service.TenantService.RegisteredTenant;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    public record RegisterTenantRequest(
            String name,
            String tenantKey,
            String schemaName,
            String domain) {}

    /**
     * POST /api/tenants
     * Body: { "name": "...", "tenantKey": "...", "schemaName": "...", "domain": "..." }
     *
     * The response contains the generated DB password for [schemaName_user].
     * It is returned only this once, so the caller must store it securely.
     */
    @PostMapping("/register")
    public ResponseEntity<RegisteredTenant> register(@RequestBody RegisterTenantRequest request) {
        RegisteredTenant created = tenantService.registerTenant(
                request.name(),
                request.tenantKey(),
                request.schemaName(),
                request.domain());

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Validation problems and duplicates -> 400 with the message
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    // Provisioning failures -> 500 without leaking SQL details to the client
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleFailure(IllegalStateException e) {
        e.printStackTrace(); // replace with your logger
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Tenant registration failed"));
    }
}