package com.example.beautysalonRESTAPI.backend.api.Company.Employees;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.beautysalonRESTAPI.backend.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.backend.model.Employees;
import com.example.beautysalonRESTAPI.backend.model.attendance;
import com.example.beautysalonRESTAPI.backend.repository.AttendanceRepository;
import com.example.beautysalonRESTAPI.backend.repository.EmployeesRepository;
import com.example.beautysalonRESTAPI.backend.security.JwtUtil;
import com.example.beautysalonRESTAPI.backend.service.QrSessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth/employee/attendance")
public class AttendanceController {

    @Autowired
    QrSessionService SessionService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

  @GetMapping("/generate-qr_code")
  public ResponseEntity<Map<String, String>> sendQrCode(){
    return ResponseEntity.ok(Map.of("code", SessionService.generateQrCode()));
  }
  
@PostMapping("/validate-qr_code")
public ResponseEntity<Map<String, String>> validateQrCode(@RequestBody QrSessionDTO request) {

    if (!SessionService.validateQr(request.getCode())) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid or expired QR code"));
    }

    Employees employee = employeesRepository.findById(request.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    String token = jwtUtil.generateTokenWithAttendance(
            request.getId(),
            request.getUsername(),
            "EMPLOYEE",
            request.getCode()
    );

    attendance attendance = new attendance();
    attendance.setEmployees(employee);
    attendanceRepository.save(attendance);

    return ResponseEntity.ok(Map.of("attendanceToken", token));
}

}
