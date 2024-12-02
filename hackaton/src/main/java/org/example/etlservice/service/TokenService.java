package org.example.etlservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.example.etlservice.model.Account;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    // Secret key for signing the JWT
    private final String SECRET_KEY = "my-secret-key";

    // Token expiration time (e.g., 1 hour)
    private final long EXPIRATION_TIME = 3600000;

    /**
     * Generate a JWT token for an account.
     */
    public String generateToken(Account account) {
        return Jwts.builder()
                .setSubject(account.getId().toString())
                .claim("username", account.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validate a JWT token.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract account ID from a token.
     */
    public UUID extractAccountId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return UUID.fromString(claims.getSubject());
    }
}
