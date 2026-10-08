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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.dto.OtpClient;
import com.example.beautysalonRESTAPI.dto.QrSessionDTO;
import com.example.beautysalonRESTAPI.dto.Clients.ClientLogin;
import com.example.beautysalonRESTAPI.model.AdminUser;
import com.example.beautysalonRESTAPI.model.Aprovals;
import com.example.beautysalonRESTAPI.model.Client;
import com.example.beautysalonRESTAPI.model.Employees;
import com.example.beautysalonRESTAPI.repository.AdminUserRepository;
import com.example.beautysalonRESTAPI.repository.AprovalsRepository;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.beautysalonRESTAPI.service.TenantService;
import com.example.beautysalonRESTAPI.dto.TenantInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import com.example.beautysalonRESTAPI.Configuration.TenantContext;

@Tag(name = "Autentikimi", description = "Autentikimi i përdoruesve të platformës")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
private SimpMessagingTemplate messagingTemplate
;
     @Autowired
    QrSessionService SessionService;

      @Autowired
    private AdminUserRepository adminRepo;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EmployeesRepository employeeRepo;

        @Autowired
    private SmsService smsservice;

    @Autowired ApprovalService approvalService;

    @Autowired
    LoginOTPService loginOtpService;
        

@Autowired
private AprovalsRepository aprovalsRepo;
    //@Autowired
   // private AuthenticationManager authenticationManager;

   private final AuthenticationManager adminAuthManager;
private final AuthenticationManager clientAuthManager;
private final AuthenticationManager employeeAuthManager;
private final TenantService tenantService;

   public AuthController(
    @Qualifier("adminAuthManager") AuthenticationManager adminAuthManager,
    @Qualifier("clientAuthManager") AuthenticationManager clientAuthManager,
    @Qualifier("employeeAuthManager") AuthenticationManager employeeAuthManager,
      TenantService tenantService
) {
    this.adminAuthManager = adminAuthManager;
    this.clientAuthManager = clientAuthManager;
    this.employeeAuthManager = employeeAuthManager;
    this.tenantService = tenantService;
}
    @Autowired
    private JwtUtil jwtUtil;

@Operation(
    summary = "Kyçja Admin",
    description = "Autentikohet përmes username dhe password, gjenerohet access dhe refresh token"
)
@PostMapping("/login/admin")
public ResponseEntity<?> loginAdmin(
        @RequestBody AuthRequest request,
        HttpServletResponse response) {

    TenantInfo tenant;

    try {
        // 1. Find tenant from central dbo.Tenants
        tenant = tenantService.findByKey(request.getTenantKey());

    } catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid tenant");
    }

    // 2. Set tenant schema BEFORE authentication
    TenantContext.setTenant(tenant.schemaName());

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
                tenant.id()
        );

        String accessToken = jwtUtil.generateToken(
                adminUser.getId(),
                adminUser.getUsername(),
                "ROLE_ADMIN",
                tenant.id()
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

    String otp = smsservice.generateOTP();

    // approvalService.createOtp(otp, client.getNumriTelefonit(), null);
    loginOtpService.createLoginOTP(otp, response.getNumri_telefonit());
      //smsservice.sendOtp(response.getNumri_telefonit(), otp);
      System.out.println("OTP: " + otp);
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
    return ResponseEntity.ok(Map.of("code", SessionService.generateQrCode()));
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

    if (!SessionService.validateQr(request.getCode())) {
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
