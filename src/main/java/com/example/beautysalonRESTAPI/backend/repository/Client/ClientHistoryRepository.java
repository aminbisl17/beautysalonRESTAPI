package com.example.beautysalonRESTAPI.backend.repository.Client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.backend.model.Client;
import com.example.beautysalonRESTAPI.backend.model.ClientHistory;

@Repository
public interface ClientHistoryRepository extends JpaRepository<ClientHistory, Long> {

@Query(value="Exec clientHistory :ClientId", nativeQuery= true)
List<Object[]> getHistoryByClientIdNative(@Param("ClientId") Long ID);

 @Query(value ="select h.emri_sherbimit, h.emri_atributit, h.pagesa, h.qmimiBazik, h.zbritja, h.pershkrimi, h.data_sherbimit where h.ID = :ID", nativeQuery = true)
 List<Object[]> getSpecificClientHistory(@Param("ID") Long ID);
}
