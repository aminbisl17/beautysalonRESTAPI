package com.example.beautysalonRESTAPI.backend.service;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.dto.ClientHistoryDTO;
import com.example.beautysalonRESTAPI.backend.repository.ClientHistoryRepository;

@Service
public class ClientHistoryService {
    private final ClientHistoryRepository repository;

    public ClientHistoryService(ClientHistoryRepository repository) {
        this.repository = repository;
    }

    public List<ClientHistoryDTO> getClientHistoryById(Long id) {
        // List<Object[]> results = repository.getHistoryByClientIdNative(id);
        List<ClientHistoryDTO> historyList = new ArrayList<>();

        for (Object[] row : repository.getHistoryByClientIdNative(id)) {
            // System.out.println(Arrays.toString(row));
            ClientHistoryDTO dto = new ClientHistoryDTO();
            dto.setId_personit(((Number) row[0]).longValue());
            dto.setId_historiku(((Number) row[1]).intValue());
            dto.setId_sherbimit(((Number) row[2]).intValue());
            dto.setId_atributit(((Number) row[3]).intValue());
            dto.setData_sherbimit((Timestamp) row[4]); // or convert to LocalDateTime
            dto.setPagesa(((Number) row[5]).doubleValue());
            dto.setQmimiBazik(((Number) row[6]).doubleValue());
            dto.setZbritja(((Number) row[7]).intValue());
            dto.setPershkrimi((String) row[8]);
            dto.setKohezgjatja((Time) row[9]); // or convert to LocalTime
            dto.setEmri_sherbimit((String) row[10]);
            dto.setOpsioni((String) row[11]);
            historyList.add(dto);
        }

        return historyList;
    }

}