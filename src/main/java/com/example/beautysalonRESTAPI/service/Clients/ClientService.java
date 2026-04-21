package com.example.beautysalonRESTAPI.service.Clients;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;

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
        return clientRepository.findByIdWithHistory(id).orElse(null);
    }

    public void delete(Client client){
        clientRepository.delete(client);
    }
}
