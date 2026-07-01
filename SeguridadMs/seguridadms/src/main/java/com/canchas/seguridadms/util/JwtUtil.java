package com.canchas.seguridadms.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta fija (necesita al menos 256 bits para ser segura)
    private static final String SECRET_KEY_STRING = "EstaEsUnaClaveSecretaSuperSeguraParaCanchas2026QueNadieAdivinaraNunca";
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // Tiempo de expiración: 1 hora (en milisegundos)
    private static final long EXPIRATION_TIME = 3600000;

    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // El token caduca en 1 hora
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validarTokenYObtenerClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado");
        }
    }
}