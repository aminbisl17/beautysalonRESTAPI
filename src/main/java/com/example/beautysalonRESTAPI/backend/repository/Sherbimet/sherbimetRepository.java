package com.example.beautysalonRESTAPI.backend.repository.Sherbimet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beautysalonRESTAPI.backend.model.Sherbimet;

public interface sherbimetRepository extends JpaRepository<Sherbimet, Long> {
   // Optional<sherbimetAdminDTO> findAll();
   @Query("SELECT s FROM Sherbimet s LEFT JOIN FETCH s.atributet WHERE s.ID = :id")
Optional<Sherbimet> findByIdWithAttributes(@Param("id") Long id);
}
