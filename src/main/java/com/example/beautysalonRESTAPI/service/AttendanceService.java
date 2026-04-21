package com.example.beautysalonRESTAPI.service;

import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.repository.AttendanceRepository;

@Service
public class AttendanceService {
    
    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo){
        this.repo = repo;
    }

}
