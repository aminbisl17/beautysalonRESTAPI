package com.example.beautysalonRESTAPI.backend.api.Company.Mixed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.service.clients.ClientService;


@RestController
@RequestMapping("api/mixed/clients/")
public class MixedClients {

    @Autowired
    private ClientService clientService;

@DeleteMapping("delete/{id}")
public ResponseEntity<String> deleteClient(@PathVariable Long id, Authentication auth) {

    Client client = clientService.getClientById(id);
    if (client == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
    }

    String username = auth.getName();
    boolean isAdmin = auth.getAuthorities().stream()
                          .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));

    if (!isAdmin && !client.getUsername().equals(username)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot delete this client");
    }

    clientService.delete(client);
    return ResponseEntity.ok("Client deleted successfully");
}
}
