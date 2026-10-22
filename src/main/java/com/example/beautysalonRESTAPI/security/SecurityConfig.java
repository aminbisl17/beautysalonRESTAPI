package com.example.beautysalonRESTAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    @Autowired
    @Qualifier("companyDetailsService")
    private UserDetailsService companyDetailsService;


    @Bean FilterRegistrationBean<TenantFilter> tenantReg(TenantFilter f) {
    var r = new FilterRegistrationBean<>(f); r.setEnabled(false); return r;
}

@Bean FilterRegistrationBean<JwtAuthFilter> jwtReg(JwtAuthFilter f) {
    var r = new FilterRegistrationBean<>(f); r.setEnabled(false); return 
    r;
}
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

@Bean("companyAuthManager")
public AuthenticationManager companyAuthManager() {
    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider(employeeDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(provider);
}
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthFilter jwtAuthFilter,
        TenantFilter tenantFilter
) throws Exception {

    http
        .cors(cors -> {})
        .csrf(csrf -> csrf.disable())

        .authorizeHttpRequests(auth -> auth
            
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            .requestMatchers(
                "/ws/**",

                // Swagger / OpenAPI
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",

                // Authentication
                "/auth/**",

                // Public infrastructure / integrations
                "/whatsapp/**",
                "/notification/**",
                "/server/**",

                // Testing
                "/test/generate-token",
                "/test/test-token",

                // Public web data
                "/web/sherbimet/all",
                "/web/employees/all",

                // Public mixed endpoints
                "/api/mixed/sherbimet/all",
                "/api/mixed/sherbimet/atributet/**",

                // Client registration and verification
                "/api/clients/register",
                "/api/clients/verify",
                "/api/clients/fast-login&register",
                "/api/clients/verify/fast-login&register",

                // Company email
                "/company/email/send",

                // Admin registration
                "/api/admin/register"
            ).permitAll()


            // =========================================
            // 2. HAS ROLE
            // =========================================

            .requestMatchers(
                "/api/admin/data",
                "/api/admin/update",
                "/api/admin/dashboard/statistics",

                // Admin employee management
                "/api/admin/employees/register",
                "/api/admin/employees/all",
                "/api/admin/employees/delete/**",
                "/api/admin/employees/update/**"
            ).hasRole("ADMIN")

            .requestMatchers(
                "/api/employee/**"
            ).hasRole("EMPLOYEE")


            // =========================================
            // 3. HAS ANY ROLE
            // =========================================

            .requestMatchers(
                "/auth/validate-qr_code"
            ).hasAnyRole("ADMIN", "EMPLOYEE")

            .requestMatchers(
                "/api/mixed/clients/delete/*"
            ).hasAnyRole("EMPLOYEE", "CLIENT")

            .requestMatchers(
                "/com/mixed/employee/skills/**"
            ).hasAnyRole("EMPLOYEE", "ADMIN")


            // =========================================
            // 4. HAS AUTHORITY
            // =========================================

            .requestMatchers(
                "/scope/company/clients/**",
                "/tenants/**"
            ).hasAuthority("SCOPE_COMPANY")


            // =========================================
            // 5. HAS ANY AUTHORITY
            // =========================================

            .requestMatchers(
                "/api/admin/sherbimet/register",
                "/api/admin/sherbimet/delete/**",
                "/api/admin/sherbimet/update/**"
            ).hasAnyAuthority("ROLE_ADMIN", "SCOPE_COMPANY")

            .requestMatchers(
                "/api/mixed/terminet/**"
            ).hasAnyAuthority(
                "ROLE_ADMIN",
                "ROLE_EMPLOYEE",
                "ROLE_CLIENT",
                "SCOPE_COMPANY"
            )


            // =========================================
            // 6. AUTHENTICATED
            // =========================================

            .requestMatchers(
                "/api/clients/**"
            ).authenticated()


            // =========================================
            // 7. EVERYTHING ELSE
            // =========================================

            .anyRequest().authenticated()
        )

        .sessionManagement(sess ->
            sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .addFilterBefore(
            jwtAuthFilter,
            UsernamePasswordAuthenticationFilter.class
        )

        .addFilterBefore(
            tenantFilter,
            JwtAuthFilter.class
        );

    return http.build();
}
}
