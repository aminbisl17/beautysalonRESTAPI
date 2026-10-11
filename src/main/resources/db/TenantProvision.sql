
-- 1) Schema
CREATE SCHEMA [{{SCHEMA}}];
GO

-- 2) Contained database user (no server login needed; works on Azure SQL Database)
CREATE USER [{{SCHEMA}}_user] WITH PASSWORD = '{{PASSWORD}}', DEFAULT_SCHEMA = [{{SCHEMA}}];
GO

-- 3) Permissions on this schema only
--    (schema-level grants also cover tables, procedures and table types created later by the template)
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON SCHEMA::[{{SCHEMA}}] TO [{{SCHEMA}}_user];
GO