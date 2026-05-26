package com.canchas.listanegrams.controller;

import com.canchas.listanegrams.dto.ClienteBloqueadoRequestDTO;
import com.canchas.listanegrams.dto.ClienteBloqueadoResponseDTO;
import com.canchas.listanegrams.service.ClienteBloqueadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listanegra")
public class ClienteBloqueadoController {

    private final ClienteBloqueadoService service;

    public ClienteBloqueadoController(ClienteBloqueadoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> bloquear(@RequestBody ClienteBloqueadoRequestDTO dto) {
        try {
            ClienteBloqueadoResponseDTO response = service.bloquear(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Si el RUT ya existe, devolvemos un error amigable
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteBloqueadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}