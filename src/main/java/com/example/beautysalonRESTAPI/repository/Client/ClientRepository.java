package com.example.beautysalonRESTAPI.repository.Client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.model.Client;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
     Optional<Client> findByEmri(String emri);


     Optional<Client> findByEmail(String email);

     Optional<Client> findByNumriTelefonit(String numriTelefonit);
     

        @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientHistory WHERE c.ID = :id")
        Optional<Client> findByIdWithHistory(@Param("id") Long id);
}

