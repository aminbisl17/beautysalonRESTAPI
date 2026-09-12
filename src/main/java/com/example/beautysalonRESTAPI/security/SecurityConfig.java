package com.example.beautysalonRESTAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    @Qualifier("employeeDetailsService")
    private UserDetailsService employeeDetailsService;

  @Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

@Primary
@Bean("adminAuthManager")
public AuthenticationManager adminAuthManager() {
    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider(adminDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
}

@Bean("clientAuthManager")
public AuthenticationManager clientAuthManager() {
    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider(clientDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
}

@Bean("employeeAuthManager")
public AuthenticationManager employeeAuthManager() {
    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider(employeeDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
         .cors(cors -> {}) 
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    .requestMatchers("/ws/**").permitAll()
    .requestMatchers(
    "/swagger-ui/**", 
    "/v3/api-docs/**", 
    "/swagger-ui.html"
).permitAll()

    .requestMatchers("/test/generate-token").permitAll()
    .requestMatchers("/test/test-token").permitAll()
    .requestMatchers("/server/**").permitAll()
    .requestMatchers("/notification/**").permitAll()

    .requestMatchers("/web/sherbimet/all").permitAll()
    .requestMatchers("/web/employees/all").permitAll()
    
    .requestMatchers("/api/mixed/sherbimet/all").permitAll()
    .requestMatchers("/api/mixed/sherbimet/atributet/**").permitAll()
    .requestMatchers("/api/clients/register").permitAll()
    .requestMatchers("/api/clients/verify").permitAll()
    .requestMatchers("/api/clients/fast-login&register").permitAll()
    .requestMatchers("/api/clients/verify/fast-login&register").permitAll()
    .requestMatchers("/company/email/send").permitAll()

    .requestMatchers("/scope/company/clients/**").hasAuthority("SCOPE_COMPANY")
    

    .requestMatchers("/auth/refresh-token").permitAll()
    .requestMatchers("/auth/delete-refresh-token").permitAll()
    .requestMatchers("/auth/validate-qr_code")
    .hasAnyRole("ADMIN", "EMPLOYEE")

.requestMatchers("/auth/**")
    .permitAll()

    .requestMatchers("/api/admin/data").hasRole("ADMIN")
    .requestMatchers("/api/admin/register").hasRole("ADMIN")
    .requestMatchers("/api/admin/update").hasRole("ADMIN")

    .requestMatchers("/api/admin/employees/register").hasRole("ADMIN")
    .requestMatchers("/api/admin/employees/all").hasRole("ADMIN")
    .requestMatchers("/api/admin/employees/delete/**").hasRole("ADMIN")
    .requestMatchers("/api/admin/employees/update/**").hasRole("ADMIN")

    // Services (sherbimet)
   .requestMatchers("/api/admin/sherbimet/register").hasAnyAuthority("ROLE_ADMIN", "SCOPE_COMPANY")
    .requestMatchers("/api/admin/sherbimet/delete/**").hasAnyAuthority("ROLE_ADMIN", "SCOPE_COMPANY")
    .requestMatchers("/api/admin/sherbimet/update/**")
    .hasAnyAuthority("ROLE_ADMIN", "SCOPE_COMPANY")

    .requestMatchers("/api/admin/dashboard/statistics").hasRole("ADMIN")

    .requestMatchers("/api/employee/**").hasRole("EMPLOYEE")
    //.requestMatchers("/api/employee/clients/**").hasRole("EMPLOYEE")
    //.requestMatchers("/api/employee/sherbimet/all").hasRole("EMPLOYEE")


    .requestMatchers("/api/mixed/clients/delete/*")
        .hasAnyRole("EMPLOYEE", "CLIENT")

        .requestMatchers("/com/mixed/employee/skills/**").hasAnyRole("EMPLOYEE", "ADMIN")

    .requestMatchers("/api/mixed/terminet/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE", "ROLE_CLIENT", "SCOPE_COMPANY")

    .requestMatchers("/api/clients/**").hasRole("CLIENT")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
