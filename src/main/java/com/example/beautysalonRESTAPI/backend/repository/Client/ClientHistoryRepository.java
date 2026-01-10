package com.example.beautysalonRESTAPI.backend.repository.Client;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.ClientHistory;

@Repository
public interface ClientHistoryRepository extends JpaRepository<ClientHistory, Long> {

@Query(value="Exec clientHistory :ClientId", nativeQuery= true)
List<Object[]> getHistoryByClientIdNative(@Param("ClientId") Long ID);


@Query(value = "SELECT * FROM historiku WHERE ID = :id", nativeQuery = true)
List<ClientHistory> getSpecificClientHistory(@Param("id") Long id);

 //@Query(value="Select * from historiku where ID = ?",)
}
