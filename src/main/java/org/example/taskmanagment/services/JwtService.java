package org.example.taskmanagment.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();

    private Claims parseTokens (String token) {
         Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

         return claims.getPayload();
    }

    public String createToken(Authentication authentication) {
        String name = authentication.getName();
        GrantedAuthority givenAuthority = authentication.getAuthorities().iterator().next();
        String role = givenAuthority.getAuthority();
        return Jwts.builder()
                .subject(name)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = parseTokens(token);
            System.out.println("Validate token reached");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return parseTokens(token).getSubject();
    }

    public String extractRole(String token) {
        return parseTokens(token).get("role", String.class);
    }
}
