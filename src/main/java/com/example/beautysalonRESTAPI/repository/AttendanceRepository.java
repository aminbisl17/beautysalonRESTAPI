package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.model.attendance;

public interface AttendanceRepository extends JpaRepository<attendance, Long>{
    Optional<attendance> findById(Long ID);
}
