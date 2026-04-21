package com.example.beautysalonRESTAPI.repository.Sherbimet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beautysalonRESTAPI.model.Atributet_sherbimeve;

public interface AtributetSherbimeveRepository extends JpaRepository<Atributet_sherbimeve, Long>{
   
    @Query(value="SELECT * FROM atributet_sherbimeve WHERE ID = :id", nativeQuery=true)
    List<Atributet_sherbimeve> getSpecificAtributes(@Param("id") Long id);
}