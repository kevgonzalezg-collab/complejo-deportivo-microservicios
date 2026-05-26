package com.canchas.inventarioms.controller;

import com.canchas.inventarioms.dto.ImplementoRequestDTO;
import com.canchas.inventarioms.dto.ImplementoResponseDTO;
import com.canchas.inventarioms.service.ImplementoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class ImplementoController {

    private final ImplementoService service;

    public ImplementoController(ImplementoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ImplementoResponseDTO> crear(@Valid @RequestBody ImplementoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ImplementoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}