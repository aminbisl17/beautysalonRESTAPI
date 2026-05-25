package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautysalonRESTAPI.model.Aprovals;


public interface AprovalsRepository extends JpaRepository<Aprovals, Long> {
    
    Optional<Aprovals> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Aprovals a WHERE a.username = :username")
    void deleteByUsername(@Param("username") String username);

    Optional<Aprovals> findByOtp(String otp);
}
