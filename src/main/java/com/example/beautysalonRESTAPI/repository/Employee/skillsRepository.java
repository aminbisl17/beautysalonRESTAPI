package com.example.beautysalonRESTAPI.repository.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.beautysalonRESTAPI.model.skills;

@Repository
public interface skillsRepository extends JpaRepository<skills, Long>{
    
}
