package org.example.taskmanagment.config;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;

public class JwtKeyGenerator {

    public static void main(String[] args) {
        System.out.println(generateKey());
    }

    public static String generateKey() {
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        byte[] objectBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(objectBytes);
    }
}
