package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.backend.repository.ClientRepository;

    
    @Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // First: check ADMIN table
        var admin = adminRepo.findByUsername(username).orElse(null);
        if (admin != null) {
            return User.builder()
                    .username(admin.getUsername())
                    .password(admin.getUserpassword())
                    .roles("ADMIN")
                    .build();
        }

        // Then: check CLIENT table
        var client = clientRepo.findByEmri(username).orElse(null);
        if (client != null) {
            return User.builder()
                    .username(client.getUsername())
                    .password(client.getUserpassword())
                    .roles("CLIENT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }

}
