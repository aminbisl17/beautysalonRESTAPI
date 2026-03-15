package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.Admin.AdminUserRepository;

@Service("adminDetailsService")
public class AdminUserDetailsService implements UserDetailsService {

    
    @Autowired
    private AdminUserRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var admin = adminRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        return User.builder()
                .username(admin.getUsername())
                .password(admin.getUserpassword())
                .roles("ADMIN")
                .build();
    }
}