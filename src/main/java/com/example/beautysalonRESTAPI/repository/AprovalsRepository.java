package com.example.beautysalonRESTAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautysalonRESTAPI.model.Aprovals;


public interface AprovalsRepository extends JpaRepository<Aprovals, Long> {
    
 
    Optional<Aprovals> findByUsernameAndOtp(String username, String otp);

    Optional<Aprovals> findByOtp(String otp);

    @Transactional
    @Modifying
    @Query("DELETE FROM Aprovals a WHERE a.username = :username AND a.otp = :otp")
    void deleteByUsernameAndOtp(@Param("username") String username, @Param("otp") String otp);

    @Modifying
    @Query("DELETE FROM Aprovals a WHERE a.username = :username")
    void deleteByUsername(@Param("username") String username);

    @Modifying
    @Query(value = """
        DELETE FROM Aprovals
        WHERE created < DATEADD(MINUTE, -5, GETUTCDATE())
    """, nativeQuery = true)
    void deleteExpiredOtps();
}
