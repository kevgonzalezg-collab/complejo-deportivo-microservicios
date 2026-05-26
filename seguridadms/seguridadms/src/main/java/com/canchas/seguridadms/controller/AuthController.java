package com.canchas.seguridadms.controller;

import com.canchas.seguridadms.dto.AuthResponseDTO;
import com.canchas.seguridadms.dto.LoginRequestDTO;
import com.canchas.seguridadms.dto.RegistroRequestDTO;
import com.canchas.seguridadms.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            // Si el correo ya existe, devolvemos un error amigable
            return ResponseEntity.badRequest().body(new AuthResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        try {
            return ResponseEntity.ok(service.login(dto));
        } catch (RuntimeException e) {
            // Si la clave está mal o el usuario no existe, devolvemos un error 401 (No Autorizado)
            return ResponseEntity.status(401).body(new AuthResponseDTO(e.getMessage(), null));
        }
    }
}