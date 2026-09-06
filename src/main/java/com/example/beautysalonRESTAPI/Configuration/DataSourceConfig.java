package com.example.beautysalonRESTAPI.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /* 
    @Bean
    public DataSource dataSource() {
        Dotenv dotenv = Dotenv.load();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("DB_URL"));
        config.setUsername(dotenv.get("DB_USERNAME"));
        config.setPassword(dotenv.get("DB_PASSWORD"));
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return new HikariDataSource(config);
    } */

        @Bean
public DataSource dataSource() {
    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(System.getenv("DB_URL"));
    config.setUsername(System.getenv("DB_USERNAME"));
    config.setPassword(System.getenv("DB_PASSWORD"));
    config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    return new HikariDataSource(config);
}
}