package com.example.beautysalonRESTAPI.backend.security.Configuration;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.beautysalonRESTAPI.backend.security.JwtAuthFilter;

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

 
@Bean
@Primary
public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider(adminDetailsService);
    adminProvider.setPasswordEncoder(passwordEncoder());

    DaoAuthenticationProvider clientProvider = new DaoAuthenticationProvider(clientDetailsService);
    clientProvider.setPasswordEncoder(passwordEncoder());

    DaoAuthenticationProvider employeeProvider = new DaoAuthenticationProvider(employeeDetailsService);
    employeeProvider.setPasswordEncoder(passwordEncoder());

    return new ProviderManager(adminProvider, clientProvider, employeeProvider);
}


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
         .cors(cors -> {}) 
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/test/generate-token").permitAll()
                .requestMatchers("/test/test-token").permitAll()
                .requestMatchers("/web/sherbimet/all").permitAll()
                .requestMatchers("/auth/login/admin").permitAll()
                .requestMatchers("/auth/login/employee").permitAll()
                .requestMatchers("/auth/login/client").permitAll()
                .requestMatchers("/api/clients/register").permitAll()
                .requestMatchers("/auth/refresh-token").permitAll()
                .requestMatchers("/company/email/send").permitAll()

                  .requestMatchers("/api/clients/verify").permitAll()

                .requestMatchers("/api/admin/data").hasRole("ADMIN")
                .requestMatchers("/api/admin/register").hasRole("ADMIN")
                .requestMatchers("/api/admin/update").hasRole("ADMIN")
                .requestMatchers("/api/admin/employees/register").hasRole("ADMIN")
                .requestMatchers("/api/admin/employees/all").hasRole("ADMIN")
                .requestMatchers("/api/admin/employees/delete/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/employees/update/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/sherbimet/register").hasRole("ADMIN")
                .requestMatchers("/api/admin/sherbimet/update").hasRole("ADMIN")
                .requestMatchers("/api/admin/sherbimet/delete/**").hasRole("ADMIN")
                .requestMatchers("/api/admin/sherbimet/all").hasRole("ADMIN")
                .requestMatchers("/api/admin/dashboard/statistics").hasRole("ADMIN")


          //      .requestMatchers("/api/employee/clients/all").hasRole("EMPLOYEE")
                .requestMatchers("/api/employee/clients/**").hasRole("EMPLOYEE")
                .requestMatchers("/api/employee/sherbimet/all").hasRole("EMPLOYEE")

                .requestMatchers("/api/mixed/sherbimet/all").permitAll() //hasAnyRole("ADMIN","EMPLOYEE")
             //   .requestMatchers("/api/admin/sherbimet/all").hasAnyRole("ADMIN", "EMPLOYEE")
               // .requestMatchers("/api/clientsHistory/**").hasAnyRole("CLIENT", "EMPLOYEE")
                .requestMatchers("/api/mixed/clients/delete/*").hasAnyRole("EMPLOYEE", "CLIENT")
                .requestMatchers("/api/mixed/terminet/create").hasAnyRole("EMPLOYEE", "CLIENT")
                .requestMatchers("/api/clients/**").hasRole("CLIENT")
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}