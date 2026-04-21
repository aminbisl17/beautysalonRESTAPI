package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beautysalonRESTAPI.model.qr_session;

public interface QrSessionRepository extends JpaRepository<qr_session, Long>{
    Optional<qr_session> findByCode(String code);
}
