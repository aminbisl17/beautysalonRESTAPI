package com.example.beautysalonRESTAPI.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 

       /* @Bean
public PasswordEncoder passwordEncoder() {
    // WARNING: This is only for testing. Do NOT use in production!
    return NoOpPasswordEncoder.getInstance();
}
 */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/test/generate-token").permitAll()
                .requestMatchers("/web/sherbimet/all").permitAll()
                .requestMatchers("/auth/login").permitAll()
                  .requestMatchers("/auth/login/admin").permitAll()
                .requestMatchers("/api/clients/register").permitAll()
                .requestMatchers("/api/clients").hasRole("ADMIN")
                 .requestMatchers("/api/admin/register").hasRole("ADMIN")
                  .requestMatchers("/api/admin/sherbimet/all").hasRole("ADMIN")
                  .requestMatchers("/api/clientsHistory/**").hasAnyRole("CLIENT", "ADMIN")
                .requestMatchers("/api/clients/delete/*").hasAnyRole("ADMIN", "CLIENT")
                .requestMatchers("/api/clients/**").hasRole("CLIENT")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}