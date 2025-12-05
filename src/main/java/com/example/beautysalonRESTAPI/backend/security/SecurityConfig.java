package com.example.beautysalonRESTAPI.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

       @Autowired
    @Qualifier("adminDetailsService")
    private UserDetailsService adminDetailsService;

    @Autowired
    @Qualifier("clientDetailsService")
    private UserDetailsService clientDetailsService;

    
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
    public AuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider clientAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(clientDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(adminAuthProvider())
                .authenticationProvider(clientAuthProvider())
                .build();
    }
 /*    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }*/

  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/test/generate-token").permitAll()
                .requestMatchers("/web/sherbimet/all").permitAll()
            //    .requestMatchers("/auth/login").permitAll()
                  .requestMatchers("/auth/login/admin").permitAll()
                    .requestMatchers("/auth/login/client").permitAll()
                .requestMatchers("/api/clients/register").permitAll()
                 .requestMatchers("/api/admin/employees/register").hasRole("ADMIN")
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