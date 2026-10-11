-- ============================================================
-- Run BEFORE TenantTemplate.sql, once per new tenant.
-- Placeholders the backend must replace:
--   {{SCHEMA}}   validated tenant name (letters/digits/underscore only)
--   {{PASSWORD}} generated password for the tenant user
-- Each batch below is separated by GO, so the backend must split on GO
-- and run the batches one by one (CREATE SCHEMA must be alone in its batch).
-- ============================================================

-- 1) Schema
CREATE SCHEMA [{{SCHEMA}}];
GO

-- 2) Login (server level) + user (database level)
--    Azure SQL Database: run the CREATE LOGIN in master, or use a contained user instead:
--    CREATE USER [{{SCHEMA}}_user] WITH PASSWORD = '{{PASSWORD}}', DEFAULT_SCHEMA = [{{SCHEMA}}];
CREATE LOGIN [{{SCHEMA}}_user] WITH PASSWORD = '{{PASSWORD}}', CHECK_POLICY = ON;
GO

CREATE USER [{{SCHEMA}}_user] FOR LOGIN [{{SCHEMA}}_user] WITH DEFAULT_SCHEMA = [{{SCHEMA}}];
GO

-- 3) Permissions on this schema only
--    (schema-level grants also cover tables, procedures and table types created later by the template)
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON SCHEMA::[{{SCHEMA}}] TO [{{SCHEMA}}_user];
GO
