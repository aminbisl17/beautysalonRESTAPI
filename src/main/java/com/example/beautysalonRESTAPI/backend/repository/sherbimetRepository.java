package com.example.beautysalonRESTAPI.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.backend.model.Sherbimet;

public interface sherbimetRepository extends JpaRepository<Sherbimet, Long> {
   // Optional<sherbimetAdminDTO> findAll();
}
