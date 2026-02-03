package com.example.beautysalonRESTAPI.backend.security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOriginPatterns(
                        "http://localhost:8080", 
                        "https://192.168.*.*:8080",
                        "http://192.168.*.*:8080"
                    )
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .exposedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}