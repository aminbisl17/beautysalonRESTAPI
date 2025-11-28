package com.example.beautysalonRESTAPI.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.beautysalonRESTAPI.backend.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
     Optional<Client> findByEmri(String emri);
     Optional<Client> findByUsername(String username);
}