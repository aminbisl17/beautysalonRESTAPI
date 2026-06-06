package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautysalonRESTAPI.model.LoginOTP;

@Repository
public interface LoginOTPRepository extends JpaRepository<LoginOTP, Long> {

    Optional<LoginOTP> findByNumriTelefonit(String numriTelefonit);

    @Query("""
        SELECT l FROM LoginOTP l
        WHERE l.otp = :otp
        AND l.numriTelefonit = :numriTelefonit
    """)
    Optional<LoginOTP> findByOTPandNumriTelefonit(
            @Param("otp") String otp,
            @Param("numriTelefonit") String numriTelefonit
    );

    
    @Transactional
    @Modifying
    @Query("""
        DELETE FROM LoginOTP l
        WHERE l.numriTelefonit = :numriTelefonit
        AND l.otp = :otp
    """)
    void deleteByNumriTelefonitAndOtp(
            @Param("otp") String otp,
            @Param("numriTelefonit") String numriTelefonit
    );


    @Modifying
    @Query(value = """
        DELETE FROM LoginOTP
        WHERE CreatedAt < DATEADD(MINUTE, -5, GETUTCDATE())
    """, nativeQuery = true)
    void deleteExpiredOtps();
}