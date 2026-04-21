package com.example.beautysalonRESTAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.model.Terminet;

public interface TerminetRepository extends JpaRepository<Terminet, Long> {
    
}
