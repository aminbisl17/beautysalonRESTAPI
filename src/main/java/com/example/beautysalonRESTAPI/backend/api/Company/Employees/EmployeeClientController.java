package com.example.beautysalonRESTAPI.backend.api.Company.Employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Clients.ClientDTO;
import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.service.clients.ClientService;

@RestController
@RequestMapping("/api/employee/clients")
public class EmployeeClientController {
    
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
 public List<ClientDTO> getAllClientsDTO() {
    return clientService.getAllClients()
            .stream()
            .map(this::toDTO)
            .toList();
}

private ClientDTO toDTO(Client client) {
    ClientDTO dto = new ClientDTO(client);
  /*   dto.setId(client.getId());
    dto.setEmri(client.getEmri());
    dto.setMbiemri(client.getMbiemri());
    dto.setGjinia(client.getGjinia());
    dto.setUsername(client.getUsername());
    dto.setNumriTelefonit(client.getNumriTelefonit());
    dto.setDataRegjistrimit(client.getDataRegjistrimit()); */
    return dto;
}
}
