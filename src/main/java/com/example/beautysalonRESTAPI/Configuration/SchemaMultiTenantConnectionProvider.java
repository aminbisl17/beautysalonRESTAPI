package com.example.beautysalonRESTAPI.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.sql.Statement; 
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;
@Component
public class SchemaMultiTenantConnectionProvider
        implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;   // single injected DataSource

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }
    @Override public void releaseAnyConnection(Connection c) throws SQLException { c.close(); }

    @Override
    public Connection getConnection(String tenant) throws SQLException {
        Connection c = dataSource.getConnection();
        try (Statement s = c.createStatement()) {
            s.execute("EXECUTE AS USER = '" + tenant + "_user'");
        } catch (SQLException e) { c.close(); throw e; }
        return c;
    }

    @Override
    public void releaseConnection(String tenant, Connection c) throws SQLException {
        try (Statement s = c.createStatement()) { s.execute("REVERT"); }
        finally { c.close(); }
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