package com.example.beautysalonRESTAPI.service;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.repository.CompanyAuthRepository;

@Service("companyDetailsService")
public class ComapnyUserDetailsService implements UserDetailsService{
    
    @Autowired 
    private  CompanyAuthRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var client = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

        return User.builder()
                .username(client.getUsername())
                .password(client.getPasswordHash())
                .roles("COMPANY")
                .build();
    }
}
