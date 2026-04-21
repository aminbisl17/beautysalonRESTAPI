package com.example.beautysalonRESTAPI.service;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class QrSessionService {

 public Set<String> QRcode = new HashSet<>();

    public String generateQrCode() {
    QRcode.clear();
    String code;

do {
    code = RandomStringUtils.randomAlphanumeric(6).toUpperCase();
} while (!QRcode.add(code)); 
    return code;
}

public boolean validateQr(String code) {

    if (!QRcode.contains(code)){
        //repo.delete(session);
        throw new RuntimeException("QR expired");
    }
          QRcode.remove(code);

 //   repo.delete(session);
    return true;
}

}
