package com.example.beautysalonRESTAPI.backend.service.Employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;

@Service("employeeDetailsService")
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeesRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var client = employeeRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

        return User.builder()
                .username(client.getUsername())
                .password(client.getUserpassword())
                .roles("EMPLOYEE")
                .build();
    }
}