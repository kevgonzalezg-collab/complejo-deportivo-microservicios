package com.canchas.seguridadms.controller;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.service.AuthService;
import com.canchas.seguridadms.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;
    private final JwtUtil jwtUtil; // Inyectamos el motor JWT

    public AuthController(AuthService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponseDTO> registrar(@RequestBody RegistroRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.registrar(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new AuthResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.login(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new AuthResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(service.listarTodasCredenciales());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestHeader(value = "Authorization", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("valido", false, "error", "Token no enviado"));
        }

        try {
            String tokenLimpio = token;
            if (token.startsWith("Bearer ")) {
                tokenLimpio = token.substring(7);
            }

            // Aquí el JwtUtil verifica la firma y que la hora no haya expirado
            Claims claims = jwtUtil.validarTokenYObtenerClaims(tokenLimpio);

            return ResponseEntity.ok(Map.of(
                    "valido", true,
                    "email", claims.getSubject(),
                    "rol", claims.get("rol")
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("valido", false, "error", e.getMessage()));
        }
    }
}