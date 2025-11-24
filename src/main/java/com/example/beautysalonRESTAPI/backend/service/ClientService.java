package com.example.beautysalonRESTAPI.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.repository.ClientRepository;

@Service
public class ClientService {
    
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Fetch all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Fetch client by ID
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
