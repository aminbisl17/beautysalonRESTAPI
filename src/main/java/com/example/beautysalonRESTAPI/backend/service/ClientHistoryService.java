package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.model.ClientHistory;
import com.example.beautysalonRESTAPI.backend.repository.ClientHistoryRepository;

@Service
public class ClientHistoryService {

    private final ClientHistoryRepository clientHistoryRepository;

    public ClientHistoryService(ClientHistoryRepository clientHistoryRepository) {
        this.clientHistoryRepository = clientHistoryRepository;
    }

    public ClientHistory getClientHistoryById(Long id) {
        return clientHistoryRepository.findById(id).orElse(null);
    }
}