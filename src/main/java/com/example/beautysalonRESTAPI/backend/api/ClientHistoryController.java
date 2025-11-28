package com.example.beautysalonRESTAPI.backend.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.backend.repository.ClientRepository;
import com.example.beautysalonRESTAPI.backend.service.ClientHistoryService;

@RestController
@RequestMapping("/api/clientsHistory")
public class ClientHistoryController {

    private final ClientHistoryService clientHistoryService;
    private final ClientRepository clientRepository; // <-- inject this

    public ClientHistoryController(ClientHistoryService clientHistoryService,
                                   ClientRepository clientRepository) {
        this.clientHistoryService = clientHistoryService;
        this.clientRepository = clientRepository; // <-- initialize it
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ClientHistoryDTO>> getClientHistoryById(
            @PathVariable Long id, Authentication auth) {

        // Check if user is an admin
        boolean isAdmin = auth.getAuthorities().stream()
                              .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // If not admin, get the authenticated client's ID
            Long clientId = clientRepository.findByUsername(auth.getName())
                    .orElseThrow(() -> new RuntimeException("Client not found"))
                    .getId();

            // If the requested ID does not match the authenticated client's ID, forbid access
            if (!id.equals(clientId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        List<ClientHistoryDTO> historyList = clientHistoryService.getClientHistoryById(id);
        return ResponseEntity.ok(historyList);
    }
}