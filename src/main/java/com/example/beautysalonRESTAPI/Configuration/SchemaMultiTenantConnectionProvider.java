package com.example.beautysalonRESTAPI.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

@Component
public class SchemaMultiTenantConnectionProvider
        implements MultiTenantConnectionProvider<String> {

    // Keys must match the identifiers returned by TenantIdentifierResolver.
    private final Map<String, DataSource> tenantDataSources;

    public SchemaMultiTenantConnectionProvider(
            Map<String, DataSource> tenantDataSources) {
        this.tenantDataSources = tenantDataSources;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return tenantDataSources.values().iterator().next().getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        DataSource dataSource = tenantDataSources.get(tenantIdentifier);
        if (dataSource == null) {
            throw new SQLException("Unknown tenant: " + tenantIdentifier);
        }
        return dataSource.getConnection();
    }

    @Override
    public void releaseConnection(
            String tenantIdentifier, Connection connection) throws SQLException {
        connection.close(); // Returns it to that tenant's pool.
            }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return unwrapType.isInstance(this);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (unwrapType.isInstance(this)) {
            return unwrapType.cast(this);
        }
        throw new IllegalArgumentException("Cannot unwrap to " + unwrapType);
    }
}