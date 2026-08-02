package com.example.beautysalonRESTAPI.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.beautysalonRESTAPI.model.LoginOTP;
import com.example.beautysalonRESTAPI.repository.LoginOTPRepository;

@Service
public class LoginOTPService {
    
    private final LoginOTPRepository loginOtpRepo;

    public LoginOTPService(LoginOTPRepository otpRepo){
        this.loginOtpRepo = otpRepo;
    }

    
    @Transactional
    public Boolean createLoginOTP(String otp, String numri_telefonit){

        loginOtpRepo.deleteExpiredOtps();

        try{
              
            LoginOTP logins = new LoginOTP();
            logins.setOtp(otp);
            logins.setNumriTelefonit(numri_telefonit);

            loginOtpRepo.saveAndFlush(logins);

        } catch(Exception e){
          throw new RuntimeException(e);
        }
        return true;
    }

@Transactional(noRollbackFor = ResponseStatusException.class)
public Boolean validateLoginOTP(String otp, String numri_telefonit) {
    LoginOTP loginOtp = loginOtpRepo
            .findByOTPandNumriTelefonit(otp, numri_telefonit)
            .orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "OTP does not exist or is incorrect"
                    )
            );

   LocalDateTime cutoff = LocalDateTime.now(ZoneOffset.UTC).minusMinutes(5);

if (loginOtp.getCreatedAt().isBefore(cutoff)) {
     loginOtpRepo.deleteByNumriTelefonitAndOtp(otp,numri_telefonit);
        throw new ResponseStatusException(
                HttpStatus.GONE,
                "OTP has expired"
        );
    }
     
    loginOtpRepo.deleteByNumriTelefonitAndOtp(otp, numri_telefonit);

    return true;
}
}
