package com.example.beautysalonRESTAPI.backend.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.ClientHistory;
import com.example.beautysalonRESTAPI.backend.model.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.backend.service.ClientHistoryService;

@RestController
@RequestMapping("/api/clientsHistory")
public class ClientHistoryController {
    
    private final ClientHistoryService clientHistoryService;

    public ClientHistoryController(ClientHistoryService clientHistoryService){
            this.clientHistoryService = clientHistoryService;
    }

      
    @GetMapping("/{id}")
    public List<ClientHistoryDTO> getClientHistoryById(@PathVariable Long id) {
        return clientHistoryService.getClientHistoryById(id);
    }
}
