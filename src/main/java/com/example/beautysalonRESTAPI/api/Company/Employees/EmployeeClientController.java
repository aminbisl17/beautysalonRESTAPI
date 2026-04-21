package com.example.beautysalonRESTAPI.api.Company.Employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/employee/clients")
public class EmployeeClientController {
    
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


@GetMapping("/{id}")
public List<ClientHistoryDTO> getClientHistory(@PathVariable Long id) {
    return historyRepo.getSpecificClientHistory(id).stream().map(ClientHistoryDTO::new).toList();
}

private ClientDTO toDTO(Client client) {
    ClientDTO dto = new ClientDTO(client);
    return dto;
}
}
