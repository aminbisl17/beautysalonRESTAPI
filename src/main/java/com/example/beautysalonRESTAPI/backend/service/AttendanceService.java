package com.example.beautysalonRESTAPI.backend.service;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.repository.AttendanceRepository;

@Service
public class AttendanceService {
    
    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo){
        this.repo = repo;
    }

}
