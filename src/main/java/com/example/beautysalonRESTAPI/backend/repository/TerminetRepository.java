package com.example.beautysalonRESTAPI.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.backend.model.Terminet;

public interface TerminetRepository extends JpaRepository<Terminet, Long> {
    
}
