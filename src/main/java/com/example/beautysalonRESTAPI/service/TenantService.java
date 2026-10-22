package com.example.beautysalonRESTAPI.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.dto.TenantDetails;
import com.example.beautysalonRESTAPI.dto.TenantInfo;

@Service
public class TenantService {

    // Letters, digits, underscore; must start with a letter. Max 50 so "<schema>_user" stays well under 128.
    private static final Pattern SCHEMA_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9_]{0,49}$");
    private static final Set<String> RESERVED_SCHEMAS =
            Set.of("dbo", "sys", "guest", "information_schema");

    // Matches a line that contains only "GO" (the SSMS batch separator, which JDBC doesn't understand)
    private static final Pattern GO_LINE = Pattern.compile("(?im)^[ \\t]*GO[ \\t]*\\r?$");

    private static final SecureRandom RANDOM = new SecureRandom();

    private final JdbcTemplate jdbcTemplate;

    public TenantService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Result of registering a tenant. The password is only available here, so store it securely. */
    public record RegisteredTenant(TenantInfo tenant, String dbUser, String dbPassword) {}

    // ------------------------------------------------------------------
    // Register a new tenant:
    //   1) creates schema [schemaName]
    //   2) creates login/user [schemaName_user] with rights on that schema only
    //   3) runs TenantTemplate.sql inside the new schema
    //   4) inserts the row into dbo.Tenants
    // Everything runs in ONE transaction (SQL Server DDL is transactional),
    // so a failure leaves nothing half-created.

    // ------------------------------------------------------------------
    public RegisteredTenant registerTenant(String name, String tenantKey, String schemaName, String domain) {

        validate(name, tenantKey, schemaName);
        checkNotTaken(tenantKey, schemaName, domain);

        String dbUser = schemaName + "_user";
        String password = generatePassword();

        List<String> batches = new ArrayList<>();
        batches.addAll(loadBatches("db/TenantProvision.sql", schemaName, password));
        batches.addAll(loadBatches("db/TenantTemplate.sql", schemaName, password));

        DataSource dataSource = jdbcTemplate.getDataSource();

        try (Connection con = dataSource.getConnection()) {
            boolean previousAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            try {
                try (Statement st = con.createStatement()) {
                    for (String batch : batches) {
                        st.execute(batch);
                    }
                }

                long id = insertTenantRow(con, name, tenantKey, schemaName, domain);

                con.commit();

                return new RegisteredTenant(
                        new TenantInfo(id, schemaName, name, tenantKey),
                        dbUser,
                        password);

            } catch (SQLException | RuntimeException e) {
                con.rollback();
                // don't include the password / script text in the message
                throw new IllegalStateException("Failed to register tenant '" + schemaName + "': " + e.getMessage(), e);
            } finally {
                con.setAutoCommit(previousAutoCommit);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Database error while registering tenant '" + schemaName + "'", e);
        }
    }

    private void validate(String name, String tenantKey, String schemaName) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tenant name is required");
        }
        if (tenantKey == null || tenantKey.isBlank()) {
            throw new IllegalArgumentException("Tenant key is required");
        }
        // The schema name is concatenated into DDL (it can't be a bind parameter), so it MUST be strictly validated.
        if (schemaName == null || !SCHEMA_PATTERN.matcher(schemaName).matches()) {
            throw new IllegalArgumentException(
                    "Schema name must start with a letter and contain only letters, digits and underscores (max 50)");
        }
        String lower = schemaName.toLowerCase(Locale.ROOT);
        if (RESERVED_SCHEMAS.contains(lower) || lower.startsWith("db_")) {
            throw new IllegalArgumentException("Schema name '" + schemaName + "' is reserved");
        }
    }

    private void checkNotTaken(String tenantKey, String schemaName, String domain) {
        Integer inTenants = jdbcTemplate.queryForObject("""
                SELECT COUNT(*) FROM dbo.Tenants
                WHERE TenantKey = ? OR SchemaName = ? OR (? IS NOT NULL AND Domain = ?)
                """, Integer.class, tenantKey, schemaName, domain, domain);
        if (inTenants != null && inTenants > 0) {
            throw new IllegalArgumentException("Tenant key, schema name or domain is already registered");
        }

        Integer schemaExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM sys.schemas WHERE name = ?", Integer.class, schemaName);
        if (schemaExists != null && schemaExists > 0) {
            throw new IllegalArgumentException("A schema named '" + schemaName + "' already exists");
        }

        Integer userExists = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM sys.database_principals WHERE name = ?", Integer.class, schemaName + "_user");
        if (userExists != null && userExists > 0) {
            throw new IllegalArgumentException("A database user '" + schemaName + "_user' already exists");
        }
    }

    private long insertTenantRow(Connection con, String name, String tenantKey, String schemaName, String domain)
            throws SQLException {
        String sql = """
                INSERT INTO dbo.Tenants (SchemaName, Name, TenantKey, Domain)
                OUTPUT INSERTED.Id
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, schemaName);
            ps.setString(2, name);
            ps.setString(3, tenantKey);
            ps.setString(4, domain);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    /** Reads a .sql file from the classpath, fills the placeholders and splits it on GO lines. */
    private List<String> loadBatches(String classpathLocation, String schemaName, String password) {
        String script;
        try {
            script = new String(
                    new ClassPathResource(classpathLocation).getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + classpathLocation, e);
        }

        script = script.replace("\uFEFF", "") // strip BOM if the file was saved with one
                .replace("{{SCHEMA}}", schemaName)
                .replace("{{PASSWORD}}", password);

        List<String> batches = new ArrayList<>();
        for (String part : GO_LINE.split(script)) {
            String batch = part.trim();
            if (!batch.isEmpty()) {
                batches.add(batch);
            }
        }
        return batches;
    }

    /** 24 chars, guaranteed to satisfy SQL Server's complexity rules, and free of quotes/braces. */
    private String generatePassword() {
        String upper = "ABCDEFGHJKLMNPQRSTUVWXYZ";
        String lower = "abcdefghijkmnpqrstuvwxyz";
        String digits = "23456789";
        String symbols = "!#$%*-_=+";
        String all = upper + lower + digits + symbols;

        List<Character> chars = new ArrayList<>();
        chars.add(upper.charAt(RANDOM.nextInt(upper.length())));
        chars.add(lower.charAt(RANDOM.nextInt(lower.length())));
        chars.add(digits.charAt(RANDOM.nextInt(digits.length())));
        chars.add(symbols.charAt(RANDOM.nextInt(symbols.length())));
        while (chars.size() < 24) {
            chars.add(all.charAt(RANDOM.nextInt(all.length())));
        }
        Collections.shuffle(chars, RANDOM);

        StringBuilder sb = new StringBuilder();
        chars.forEach(sb::append);
        return sb.toString();
    }

    // ------------------------------------------------------------------
    // Existing methods (unchanged)
    // ------------------------------------------------------------------


       public List<TenantDetails> findAll() {
        String sql = """
                SELECT Id, TenantKey, SchemaName, Name, IsActive, CreatedAt, Domain
                FROM dbo.Tenants
                ORDER BY Id DESC
                """;
 
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Timestamp created = rs.getTimestamp("CreatedAt");
            return new TenantDetails(
                    rs.getLong("Id"),
                    rs.getString("TenantKey"),
                    rs.getString("SchemaName"),
                    rs.getString("Name"),
                    rs.getBoolean("IsActive"),
                    created == null ? null : created.toLocalDateTime(),
                    rs.getString("Domain"));
        });
    }
    
    public TenantInfo findByKey(String tenantKey) {

        String sql = """
                    SELECT Id, SchemaName, Name, TenantKey
                    FROM dbo.Tenants
                    WHERE TenantKey = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new TenantInfo(
                        rs.getLong("Id"),
                        rs.getString("SchemaName"),
                        rs.getString("Name"),
                        rs.getString("TenantKey")),
                tenantKey);
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
                    tenantKey);
        } catch (Exception e) {
            return null;
        }
    }

    public TenantInfo findByDomain(String domain) {

        String sql = """
                    SELECT Id, SchemaName, Name, TenantKey
                    FROM dbo.Tenants
                    WHERE Domain = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new TenantInfo(
                        rs.getLong("Id"),
                        rs.getString("SchemaName"),
                        rs.getString("Name"),
                        rs.getString("TenantKey")),
                domain);
    }

}