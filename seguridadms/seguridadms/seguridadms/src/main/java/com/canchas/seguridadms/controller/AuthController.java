package com.canchas.seguridadms.controller;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
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
            return ResponseEntity.ok(service.login(dto)); // 👈 Corregido el flujo normal
        } catch (RuntimeException e) { // 👈 Declarada la variable 'e' correctamente
            return ResponseEntity.status(401).body(new AuthResponseDTO(e.getMessage(), null));
        }
    }

    // 🔥 EL ENDPOINT TOTALMENTE ACTUALIZADO Y FLEXIBLE
    @PostMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String token) {

        // 1. Limpiamos el prefijo "Bearer " que Postman añade automáticamente
        String tokenLimpio = token;
        if (token != null && token.startsWith("Bearer ")) {
            tokenLimpio = token.substring(7);
        }

        // 2. Comparamos el token limpio o el alternativo "1234"
        if (tokenLimpio != null && (tokenLimpio.equals("token-simulado-por-ahora") || tokenLimpio.equals("1234"))) {
            return ResponseEntity.ok(Map.of(
                    "valido", true,
                    "email", "kevis.test@gmail.com",
                    "rol", "CLIENTE"
            ));
        }

        // Si no coincide, arroja el 401 controlado
        return ResponseEntity.status(401).body(Map.of("valido", false));
    }
}