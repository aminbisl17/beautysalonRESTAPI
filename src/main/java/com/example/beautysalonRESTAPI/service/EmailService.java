package com.example.beautysalonRESTAPI.service;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.beautysalonRESTAPI.model.EmailVerificationOTP;
import com.example.beautysalonRESTAPI.repository.EmailSender;
import com.example.beautysalonRESTAPI.repository.EmailVerificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;
    private final EmailVerificationRepository emailRepo;

    public EmailService(JavaMailSender mailSender, EmailVerificationRepository emailRepo){
            this.mailSender = mailSender;
            this.emailRepo = emailRepo;
    }

@Override
public void send(String to, String subject, String message) throws MessagingException  {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    helper.setFrom("dhshcnbshc@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);

    String html = """
        <div style="font-family: Arial, sans-serif;">
            <h2>💇 Beauty Salon</h2>
            <p>%s</p>
        </div>
        """.formatted(message);

    helper.setText(html, true);

    mailSender.send(mimeMessage);
}

public void sendOtp(String to, String subject, String otp) throws MessagingException {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

    helper.setFrom("dhshcnbshc@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);

    String html = """
        <!DOCTYPE html>
        <html>
        <body style="margin:0;padding:0;background-color:#f5f5f5;font-family:Arial,sans-serif;">
            <div style="max-width:600px;margin:40px auto;background:white;border-radius:12px;
                        overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.1);">

                <div style="background:#ff69b4;padding:25px;text-align:center;color:white;">
                    <h1 style="margin:0;">💇 Beauty Salon</h1>
                </div>

                <div style="padding:40px;text-align:center;">
                    <h2 style="color:#333;">Email Verification</h2>

                    <p style="color:#666;font-size:16px;">
                        Thank you for registering with Beauty Salon.
                        Use the verification code below to complete your account verification.
                    </p>

                    <div style="
                        display:inline-block;
                        margin:25px 0;
                        padding:15px 35px;
                        background:#fff0f7;
                        border:2px dashed #ff69b4;
                        border-radius:10px;
                        font-size:32px;
                        font-weight:bold;
                        letter-spacing:8px;
                        color:#ff1493;">
                        %s
                    </div>

                    <p style="color:#666;">
                        This code will expire in <strong>5 minutes</strong>.
                    </p>

                    <p style="font-size:14px;color:#999;margin-top:30px;">
                        If you did not request this verification, you can safely ignore this email.
                    </p>
                </div>

                <div style="background:#fafafa;padding:15px;text-align:center;
                            color:#999;font-size:12px;">
                    © Beauty Salon
                </div>

            </div>
        </body>
        </html>
        """.formatted(otp);

    helper.setText(html, true);

    mailSender.send(mimeMessage);
}


@Transactional
public String verifyOtp(String email, String otp) {

    emailRepo.deleteExpiredOtps();

    Optional<EmailVerificationOTP> verification =
            emailRepo.findByEmailAndOtp(email, otp);

    if (verification.isEmpty()) {
        return "OTP does not exist, is incorrect, or has expired.";
    }

    emailRepo.deleteByEmailAndOtp(email, otp);

    return "Email verified successfully.";
}


     public String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}