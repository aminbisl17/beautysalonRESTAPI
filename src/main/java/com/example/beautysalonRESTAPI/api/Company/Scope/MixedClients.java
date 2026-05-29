package com.example.beautysalonRESTAPI.api.Company.Scope;

import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/scope/company/clients/")
public class MixedClients {

    

    @Autowired
    private ClientService clientService;

       @Autowired
    ClientHistoryRepository historyRepo;

    @Autowired
    ClientRepository clientRepo;

    @GetMapping("all")
 public List<ClientDTO> getAllClientsDTO() {
    return clientService.getAllClients()
            .stream()
            .map(this::toDTO)
            .toList();
}


@GetMapping("history/{id}")
public List<ClientHistoryDTO> getClientHistory(@PathVariable Long id) {
    return historyRepo.getSpecificClientHistory(id).stream().map(ClientHistoryDTO::new).toList();
}

private ClientDTO toDTO(Client client) {
    ClientDTO dto = new ClientDTO(client);
    return dto;
}


@DeleteMapping("delete/{id}")
public ResponseEntity<String> deleteClient(@PathVariable Long id) {

    Client client = clientService.getClientById(id);
    if (client == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
    }

    clientService.delete(client);
    return ResponseEntity.ok("Client deleted successfully");
}

@PutMapping("update")
public String updateClient(@RequestBody ClientDTO request) {
    try{
    Client client = clientService.getClientById(request.getID());
    if (client == null) {
        return "Client not found!";
    }

    client.setEmri(request.getEmri());
    client.setMbiemri(request.getMbiemri());
    client.setNumriTelefonit(request.getNumri_telefonit());
    client.setPershkrimi(request.getPershkrimi());
    client.setEmail(request.getEmail());
    client.setGjinia(request.getGjinia().toLowerCase() == "m" ? "Mashkull" : "Femer");
    clientRepo.save(client);
} catch(Exception e){
 e.printStackTrace();
}

    return "Client updated!";
}

}
