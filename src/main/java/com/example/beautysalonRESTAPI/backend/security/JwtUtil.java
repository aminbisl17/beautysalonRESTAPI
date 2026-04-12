package com.example.beautysalonRESTAPI.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

 // private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 10; //test

    private static final String SECRET = "myVeryStrongSecretKeyForJWT123456!";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public String generateToken(Long id, String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("id", id)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}

     
public String generateRefreshToken(Long id, String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("id", id)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}

public String generateCompanyToken(Long id, String username, String role) {
    return Jwts.builder()
            .setSubject(username)
            .claim("id", id)
            .claim("role", role) 
            .claim("type", "COMPANY_ACCESS")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 15 min
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}

public boolean validateRefreshToken(String token) {
    try {
        getClaims(token);  
        return true;
    } catch (Exception e) {
        return false;
    }
}

public boolean validateCompanyToken(String token) {
    try {
        String type = getClaims(token).get("type", String.class);

        if (!"COMPANY_ACCESS".equals(type)) {
            throw new RuntimeException("Invalid token type");
        }

        return true;
    } catch (Exception e) {
        return false;
    }
}

public String extractCode(String token){
    return (String)getClaims(token).get("code");
}

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extract role
    public String extractRole(String token) {
        return (String) getClaims(token).get("role");
    }

   public Long extractId(String token) {
    return getClaims(token).get("id", Long.class);
}

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractType(String token) {
    Object type = getClaims(token).get("type");
    return type != null ? type.toString() : null;
}

}