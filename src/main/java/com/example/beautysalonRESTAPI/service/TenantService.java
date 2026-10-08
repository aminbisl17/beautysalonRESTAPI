package com.example.beautysalonRESTAPI.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.dto.TenantInfo;

@Service
public class TenantService {

    private final JdbcTemplate jdbcTemplate;

    public TenantService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        public TenantInfo findByKey(String tenantKey) {

        String sql = """
            SELECT Id, SchemaName, Name
            FROM dbo.Tenants
            WHERE TenantKey = ?
        """;

        return jdbcTemplate.queryForObject(
    sql,
    (rs, rowNum) -> new TenantInfo(
        rs.getLong("Id"),
        rs.getString("SchemaName"),
        rs.getString("Name")
    ),
    tenantKey
);

    }

    public String getSchemaName(String tenantKey) {

        String sql = """
            SELECT SchemaName
            FROM dbo.Tenants
            WHERE TenantKey = ?
        """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    String.class,
                    tenantKey
            );
        } catch (Exception e) {
            return null;
        }
    }
}