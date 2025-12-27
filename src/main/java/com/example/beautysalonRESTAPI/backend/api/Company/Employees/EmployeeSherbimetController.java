package com.example.beautysalonRESTAPI.backend.api.Company.Employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetAdminDTO;
import com.example.beautysalonRESTAPI.backend.dto.Sherbimet.SherbimetEmployeeDTO;
import com.example.beautysalonRESTAPI.backend.service.SherbimetService;

@RestController
@RequestMapping("api/employee/sherbimet/")
public class EmployeeSherbimetController {
    

      @Autowired
    private SherbimetService sherbimetService;

    @GetMapping("all")
    public List<SherbimetEmployeeDTO> fetchAllSherbimet(){
        return sherbimetService.getAllServicesEmployee();
    }
    }

