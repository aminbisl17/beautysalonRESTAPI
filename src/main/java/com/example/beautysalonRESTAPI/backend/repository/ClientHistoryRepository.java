package com.example.beautysalonRESTAPI.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.ClientHistory;

@Repository
public interface ClientHistoryRepository extends JpaRepository<ClientHistory, Integer> {
    
}
