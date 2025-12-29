package org.project.todo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UUID id) {
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, secret)
                .expiration(new Date(System.currentTimeMillis() + expiration + 1000L))
                .signWith(getKey())
                .compact();

    }

    public String extractToken(String token) {
        Key key = getKey();

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseClaimsJws(token)
                .getPayload();

        return claims.getSubject();
    }

}
