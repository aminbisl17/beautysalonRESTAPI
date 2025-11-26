package com.example.beautysalonRESTAPI.backend.api;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET all clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // GET client by ID
  @GetMapping("/{id}")
public ResponseEntity<Client> getClientById(@PathVariable Long id, Authentication auth) {
    Client client = clientService.getClientById(id);

    String username = auth.getName(); // username from JWT
    boolean isAdmin = auth.getAuthorities().stream()
                          .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (!isAdmin && !client.getEmri().equals(username)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(client);
}
}