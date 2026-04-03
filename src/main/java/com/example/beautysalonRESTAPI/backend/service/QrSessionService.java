package com.example.beautysalonRESTAPI.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.example.beautysalonRESTAPI.backend.model.qr_session;
import com.example.beautysalonRESTAPI.backend.repository.QrSessionRepository;

@Service
public class QrSessionService {
    
    private final QrSessionRepository repo;

 public Set<String> QRcode = new HashSet<>();

    public QrSessionService(QrSessionRepository repo){
               this.repo = repo;
    }


    public String generateQrCode() {
  //  repo.deleteAll();
    QRcode.clear();
    String code;

do {
    code = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
} while (!QRcode.add(code)); 
   // qr_session session = new qr_session();
    //session.setCode(code);
    //session.setExpiresAt(LocalDateTime.now().plusSeconds(60));
   // repo.save(session);
    return code;
}

public boolean validateQr(String code) {

//    qr_session session = repo.findByCode(code)
      //      .orElseThrow(() -> new RuntimeException("Invalid QR"));

    if (!QRcode.contains(code)){
        //repo.delete(session);
        throw new RuntimeException("QR expired");
    }
          QRcode.remove(code);

 //   repo.delete(session);
    return true;
}

}
