package com.example.beautysalonRESTAPI.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.backend.model.attendance;

public interface AttendanceRepository extends JpaRepository<attendance, Long>{
    Optional<attendance> findById(Long ID);
}
