package com.example.beautysalonRESTAPI.api.Company.Scope;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.repository.Client.ClientHistoryRepository;
import com.example.beautysalonRESTAPI.service.Clients.ClientService;


@RestController
@RequestMapping("/scope/company/clients/")
public class MixedClients {

    @Autowired
    private ClientService clientService;

       @Autowired
    ClientHistoryRepository historyRepo;
    
    @GetMapping("/all")
 public List<ClientDTO> getAllClientsDTO() {
    return clientService.getAllClients()
            .stream()
            .map(this::toDTO)
            .toList();
}


@GetMapping("/history/{id}")
public List<ClientHistoryDTO> getClientHistory(@PathVariable Long id) {
    System.out.println(id);
    return historyRepo.getSpecificClientHistory(id).stream().map(ClientHistoryDTO::new).toList();
}

private ClientDTO toDTO(Client client) {
    ClientDTO dto = new ClientDTO(client);
    return dto;
}


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
