package com.example.beautysalonRESTAPI.api;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.OtpClient;
import com.example.beautysalonRESTAPI.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientLogin;
import com.example.beautysalonRESTAPI.model.AdminUser;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.model.CompanyAuth;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
import com.example.beautysalonRESTAPI.repository.CompanyAuthRepository;
import com.example.beautysalonRESTAPI.repository.LoginOTPRepository;
import com.example.beautysalonRESTAPI.repository.Client.ClientRepository;
import com.example.beautysalonRESTAPI.repository.Employee.EmployeesRepository;
import com.example.beautysalonRESTAPI.security.AuthRequest;
import com.example.beautysalonRESTAPI.security.JwtUtil;
import com.example.beautysalonRESTAPI.security.Responses.ClientAuthResponse;
import com.example.beautysalonRESTAPI.service.ApprovalService;
import com.example.beautysalonRESTAPI.service.LoginOTPService;
import com.example.beautysalonRESTAPI.service.QrSessionService;
import com.example.beautysalonRESTAPI.service.SmsService;

import com.example.beautysalonRESTAPI.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import com.example.beautysalonRESTAPI.Configuration.TenantContext;

@Tag(name = "Autentikimi", description = "Autentikimi i përdoruesve të platformës")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SimpMessagingTemplate messagingTemplate;
private final QrSessionService sessionService;
private final AdminUserRepository adminRepo;
private final ClientRepository clientRepo;
private final EmployeesRepository employeeRepo;
private final SmsService smsService;
private final ApprovalService approvalService;
private final LoginOTPService loginOtpService;
private final AprovalsRepository aprovalsRepo;
private final AuthenticationManager adminAuthManager;
private final AuthenticationManager clientAuthManager;
private final AuthenticationManager employeeAuthManager;
private final TenantService tenantService;
private final JwtUtil jwtUtil;
private final CompanyAuthRepository companyAuthRepo;
private final BCryptPasswordEncoder passwordEncoder;

public AuthController(

        SimpMessagingTemplate messagingTemplate,
        QrSessionService sessionService,
        AdminUserRepository adminRepo,
        ClientRepository clientRepo,
        EmployeesRepository employeeRepo,
        SmsService smsService,
        ApprovalService approvalService,
        LoginOTPService loginOtpService,
        AprovalsRepository aprovalsRepo,
        @Qualifier("adminAuthManager")
        AuthenticationManager adminAuthManager,
        @Qualifier("clientAuthManager")
        AuthenticationManager clientAuthManager,
        @Qualifier("employeeAuthManager")
        AuthenticationManager employeeAuthManager,
        TenantService tenantService,
        JwtUtil jwtUtil,
        CompanyAuthRepository companyAuthRepo,
        BCryptPasswordEncoder passwordEncoder
) {
    this.messagingTemplate = messagingTemplate;
    this.sessionService = sessionService;
    this.adminRepo = adminRepo;
    this.clientRepo = clientRepo;
    this.employeeRepo = employeeRepo;
    this.smsService = smsService;
    this.approvalService = approvalService;
    this.loginOtpService = loginOtpService;
    this.aprovalsRepo = aprovalsRepo;
    this.adminAuthManager = adminAuthManager;
    this.clientAuthManager = clientAuthManager;
    this.employeeAuthManager = employeeAuthManager;
    this.tenantService = tenantService;
    this.jwtUtil = jwtUtil;
    this.companyAuthRepo = companyAuthRepo;
    this.passwordEncoder = passwordEncoder;
}


@PostMapping("/register/company")
public ResponseEntity<?> registerCompany(
        @RequestBody AuthRequest request) {

    Long tenantId = TenantContext.getTenantId();

    if (tenantId == null) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Tenant not resolved"));
    }

    if (request.getUsername() == null
            || request.getUsername().isBlank()
            || request.getPassword() == null
            || request.getPassword().length() < 8) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error",
                        "Username is required and password must be at least 8 characters"
                ));
    }

    // tenantId is Long — use it directly
    if (companyAuthRepo.existsByTenantId(tenantId)) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error",
                        "This tenant already has a company account"
                ));
    }

    if (companyAuthRepo.existsByUsername(request.getUsername())) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Username already exists"));
    }

    CompanyAuth company = new CompanyAuth();
    company.setTenantId(tenantId);
    company.setUsername(request.getUsername());
    company.setPasswordHash(
            passwordEncoder.encode(request.getPassword())
    );

    companyAuthRepo.save(company);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                    "message",
                    "Company account registered successfully"
            ));
}

@PostMapping("/login/company")
public ResponseEntity<?> loginCompany(
        @RequestBody AuthRequest request) {

    Long tenantId = TenantContext.getTenantId();

    if (tenantId == null) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", "Tenant not resolved"));
    }

    if (request.getUsername() == null
            || request.getUsername().isBlank()
            || request.getPassword() == null
            || request.getPassword().isBlank()) {

        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error",
                        "Username and password are required"
                ));
    }

    Optional<CompanyAuth> optionalCompany =
            companyAuthRepo.findByTenantIdAndUsername(
                    tenantId,
                    request.getUsername()
            );

    if (optionalCompany.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }

    CompanyAuth company = optionalCompany.get();

    if (!passwordEncoder.matches(
            request.getPassword(),
            company.getPasswordHash())) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }

    String accessToken = jwtUtil.generateToken(
            company.getId().longValue(),
            company.getUsername(),
            "ROLE_COMPANY",
            tenantId
    );

    String refreshToken = jwtUtil.generateRefreshToken(
            company.getId().longValue(),
            company.getUsername(),
            "ROLE_COMPANY",
            tenantId
    );

    return ResponseEntity.ok(Map.of(
            "message", "Company login successful",
            "accessToken", accessToken,
            "refreshToken", refreshToken,
            "username", company.getUsername(),
            "tenantId", tenantId
    ));
}

@Operation(
    summary = "Kyçja Admin",
    description = "Autentikohet përmes username dhe password, gjenerohet access dhe refresh token"
)
@PostMapping("/login/admin")
public ResponseEntity<?> loginAdmin(
        @RequestBody AuthRequest request,
        HttpServletResponse response) {

    Long tenantId = TenantContext.getTenantId();

    if (tenantId == null) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Tenant not resolved");
    }


    try {

        // 3. Authenticate against this tenant
        adminAuthManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // 4. Find admin inside the selected tenant
        Optional<AdminUser> optionalUser =
                adminRepo.findByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        AdminUser adminUser = optionalUser.get();

        // 5. Generate tokens containing tenant ID
        String refreshToken = jwtUtil.generateRefreshToken(
                adminUser.getId(),
                adminUser.getUsername(),
                "ROLE_ADMIN",
                tenantId
        );

        String accessToken = jwtUtil.generateToken(
                adminUser.getId(),
                adminUser.getUsername(),
                "ROLE_ADMIN",
                tenantId
        );

        // 6. Refresh cookie
        ResponseCookie cookie = ResponseCookie.from(
                "adminRefreshToken",
                refreshToken
        )
        .httpOnly(true)
        .secure(true)
        .path("/")
        .maxAge(7 * 24 * 60 * 60)
        .sameSite("None")
        .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(
            Map.of(
                "refreshToken", refreshToken,
                "token", accessToken
            )
        );

    } catch (AuthenticationException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");

    } finally {

        // VERY IMPORTANT
        TenantContext.clear();
    }
}


@PostMapping("/login/client")
public ResponseEntity<?> loginClient(@RequestBody ClientLogin response) { 

   Client client = clientRepo.findByNumriTelefonit(response.getNumri_telefonit()).orElse(null);

    if(client == null){
        return ResponseEntity.badRequest().body("ky numer nuk ekziston!");
    }

    String otp = smsService.generateOTP();
    String nrTel = response.getNumri_telefonit();
    // approvalService.createOtp(otp, client.getNumriTelefonit(), null);
    loginOtpService.createLoginOTP(otp, nrTel);
      //smsservice.sendOtp(response.getNumri_telefonit(), otp);
      smsService.sentMessage(nrTel, otp);
      //System.out.println("OTP: " + otp);
    return ResponseEntity.ok("sent to verify");
}

@PostMapping("/login/client/verify")
public ResponseEntity<?> verify(@RequestBody OtpClient response, HttpServletResponse res) {

    try {
     loginOtpService.validateLoginOTP(
                response.getOtpcode(),
                response.getNumri_telefonit()
        );

        Client client = clientRepo.findByNumriTelefonit(response.getNumri_telefonit())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

Long tenantId = TenantContext.getTenantId();

        String accessToken = jwtUtil.generateToken(
                client.getId(),
                client.getNumriTelefonit(),
                "ROLE_CLIENT",
                  tenantId
        );

        String refreshToken = jwtUtil.generateRefreshToken(
                client.getId(),
                client.getNumriTelefonit(),
                "ROLE_CLIENT",
                  tenantId
        );

       /* ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(30 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();
 */

     ResponseCookie cookie = ResponseCookie.from("clientRefreshToken", refreshToken)
    .httpOnly(true)
    .secure(true)
    .path("/")
    .maxAge(30 * 24 * 60 * 60)
    .sameSite("None")
    .build();
        res.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok(new ClientAuthResponse(accessToken));

    } catch (ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of("message", ex.getReason()));

    } catch (Exception ex) {
        return ResponseEntity
                .status(500)
                .body(
                    Map.of("message", ex.getMessage()));
    }
}

@PostMapping("/login/employee")
public ResponseEntity<?> loginEmployee(@RequestBody AuthRequest request) {

    try {

        employeeAuthManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Optional<Employees> optionalEmployee =
                employeeRepo.findByUsername(request.getUsername());

        if (optionalEmployee.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        Employees employee = optionalEmployee.get();

        Long tenantId = TenantContext.getTenantId();

        if (tenantId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Tenant not found");
        }

        String refreshToken = jwtUtil.generateRefreshToken(
                employee.getID(),
                employee.getUsername(),
                "ROLE_EMPLOYEE",
                tenantId
        );

        String accessToken = jwtUtil.generateToken(
                employee.getID(),
                employee.getUsername(),
                "ROLE_EMPLOYEE",
                tenantId
        );

        return ResponseEntity.ok(
                Map.of(
                        "refreshToken", refreshToken,
                        "token", accessToken
                )
        );

    } catch (AuthenticationException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }
}

@GetMapping("/generate-qr_code")
  public ResponseEntity<Map<String, String>> sendQrCode(){
    return ResponseEntity.ok(Map.of("code", sessionService.generateQrCode()));
  }


@PostMapping("/validate-qr_code")
public ResponseEntity<?> validateQrCode(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody QrSessionDTO request) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Missing or invalid Authorization header");
    }

    String token = authHeader.substring(7);

    if (!jwtUtil.validateToken(token)) {
        return ResponseEntity.status(401).body("Invalid or expired token");
    }

    if (!sessionService.validateQr(request.getCode())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid or expired QR code"));
    }

    String role = jwtUtil.extractRole(token);
    Long id = jwtUtil.extractId(token);

    Object response;

    if ("ROLE_EMPLOYEE".equals(role) || "ROLE_ADMIN".equals(role)) {

    String username = jwtUtil.extractUsername(token);

    String cotoken = jwtUtil.generateCompanyToken(
            id,
            username,
            role
    );

    response = Map.of(
            "token", cotoken,
            "role", role,
            "username", username
    );
}
    else {
        return ResponseEntity.status(403)
                .body(Map.of("error", "Unauthorized role"));
    }

    messagingTemplate.convertAndSend("/topic/qr/" + request.getCode(),response);

    return ResponseEntity.ok(response);
}


@PostMapping("/delete-refresh-token")
public ResponseEntity<?> deleteRefreshToken(HttpServletResponse response) {

    String[] cookieNames = {
        "adminRefreshToken",
        "clientRefreshToken",
        "employeeRefreshToken",
        "refreshToken" // remove this after your old cookie is fully migrated
    };

    for (String cookieName : cookieNames) {

        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    return ResponseEntity.noContent().build();
}
    
@PostMapping("/refresh-token")
public ResponseEntity<?> refresh(
        @CookieValue(value = "adminRefreshToken", required = false) String adminToken,
        @CookieValue(value = "clientRefreshToken", required = false) String clientToken,
        @CookieValue(value = "employeeRefreshToken", required = false) String employeeToken,
        @RequestBody(required = false) Map<String, String> body
) {

    String refreshToken = null;

    if (adminToken != null) {
        refreshToken = adminToken;
    } else if (clientToken != null) {
        refreshToken = clientToken;
    } else if (employeeToken != null) {
        refreshToken = employeeToken;
    } else if (body != null) {
        refreshToken = body.get("refreshToken");
    }

    if (refreshToken == null ||
            !jwtUtil.validateRefreshToken(refreshToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(refreshToken);
    String role = jwtUtil.extractRole(refreshToken);
    Long id = jwtUtil.extractId(refreshToken);
    Long tenantId = jwtUtil.extractTenantId(refreshToken);

    String newAccessToken = jwtUtil.generateToken(
            id,
            username,
            role,
            tenantId
    );

    return ResponseEntity.ok(
            Map.of("accessToken", newAccessToken)
    );
}

@PostMapping("/refresh-token-admin")
public ResponseEntity<?> refreshTokenAdmin(
        @CookieValue(
                value = "adminRefreshToken",
                required = false
        ) String adminToken,
        @RequestBody(required = false) Map<String, String> body
) {

    if (adminToken == null ||
            !jwtUtil.validateRefreshToken(adminToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(adminToken);
    String role = jwtUtil.extractRole(adminToken);
    Long id = jwtUtil.extractId(adminToken);
    Long tenantId = jwtUtil.extractTenantId(adminToken);

    String accessToken = jwtUtil.generateToken(
            id,
            username,
            role,
            tenantId
    );

    return ResponseEntity.ok(
            Map.of("accessToken", accessToken)
    );
}

@PostMapping("/refresh-token-employee")
public ResponseEntity<?> refreshTokenEmployee(
        @RequestBody(required = false) Map<String, String> body
) {

    String empToken =
            (body != null) ? body.get("refreshToken") : null;

    if (empToken == null ||
            !jwtUtil.validateRefreshToken(empToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(empToken);
    String role = jwtUtil.extractRole(empToken);
    Long id = jwtUtil.extractId(empToken);
    Long tenantId = jwtUtil.extractTenantId(empToken);

    String accessToken = jwtUtil.generateToken(
            id,
            username,
            role,
            tenantId
    );

    return ResponseEntity.ok(
            Map.of("accessToken", accessToken)
    );
}

@PostMapping("/refresh-token-client")
public ResponseEntity<?> refreshTokenClient(
        @CookieValue(
                value = "clientRefreshToken",
                required = false
        ) String clientToken
) {

    if (clientToken == null ||
            !jwtUtil.validateRefreshToken(clientToken)) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid refresh token"));
    }

    String username = jwtUtil.extractUsername(clientToken);
    String role = jwtUtil.extractRole(clientToken);
    Long id = jwtUtil.extractId(clientToken);
    Long tenantId = jwtUtil.extractTenantId(clientToken);

    String accessToken = jwtUtil.generateToken(
            id,
            username,
            role,
            tenantId
    );

    return ResponseEntity.ok(
            Map.of("accessToken", accessToken)
    );
}

}
