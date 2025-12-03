package com.example.beautysalonRESTAPI.backend.repository;

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
@Query(value = "SELECT h.ID, h.id_historikut, h.Sherbimi, h.id_atributit, h.Data_sherbimit, " +
               "h.Pagesa, h.QmimiBazik, h.Zbritja, h.Pershkrimi, h.Kohezgjatja, " +
               "s.emri_sherbimit, a.opsioni " +
               "FROM beautysalon.historiku h " +
               "JOIN beautysalon.sherbimet s ON s.ID = h.Sherbimi " +
               "LEFT JOIN beautysalon.atributet_sherbimeve a ON a.id_atributit = h.id_atributit " +
               "WHERE h.ID = :ID", 
       nativeQuery = true)
List<Object[]> getHistoryByClientIdNative(@Param("ID") Long ID);

 @Query(value ="select s.emri_sherbimit, a.opsioni, h.pagesa, h.qmimiBazik, h.zbritja, h.pershkrimi, h.data_sherbimit where h.ID = :ID", nativeQuery = true)
 List<Object[]> getSpecificClientHistory(@Param("ID") Long ID);
}
