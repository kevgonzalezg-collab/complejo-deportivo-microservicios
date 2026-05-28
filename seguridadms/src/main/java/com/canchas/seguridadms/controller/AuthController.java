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
    public ResponseEntity<?> validarToken(@RequestHeader("Authorization") String token) {


        String tokenLimpio = token;
        if (token != null && token.startsWith("Bearer ")) {
            tokenLimpio = token.substring(7);
        }


        if (tokenLimpio != null && (tokenLimpio.equals("token-simulado-por-ahora") || tokenLimpio.equals("1234"))) {
            return ResponseEntity.ok(Map.of(
                    "valido", true,
                    "email", "kevis.test@gmail.com",
                    "rol", "CLIENTE"
            ));
        }


        return ResponseEntity.status(401).body(Map.of("valido", false));
    }
}