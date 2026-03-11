package com.example.beautysalonRESTAPI.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.backend.model.Aprovals;

public interface AprovalsRepository extends JpaRepository<Aprovals, Long> {
    
    Optional<Aprovals> findByUsername(String username);
}
