package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautysalonRESTAPI.model.EmailVerificationOTP;
@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationOTP, Long>{
    

    Optional<EmailVerificationOTP> findByEmailAndOtp(String email, String otp);

    Optional<EmailVerificationOTP> findByOtp(String otp);

    @Transactional
    @Modifying
    @Query("DELETE FROM EmailVerificationOTP e WHERE e.email = :email AND e.otp = :otp")
    void deleteByEmailAndOtp(@Param("email") String email, @Param("otp") String otp);

    @Transactional
    @Modifying
    @Query("DELETE FROM EmailVerificationOTP e WHERE e.email = :email")
    void deleteByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE FROM emailVerificationOTP
        WHERE createdAt < DATEADD(MINUTE, -5, GETUTCDATE())
    """, nativeQuery = true)
    void deleteExpiredOtps();
}

