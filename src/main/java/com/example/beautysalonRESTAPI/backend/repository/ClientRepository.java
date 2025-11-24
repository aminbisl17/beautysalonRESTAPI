package com.example.beautysalonRESTAPI.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // You can add custom queries if needed
}