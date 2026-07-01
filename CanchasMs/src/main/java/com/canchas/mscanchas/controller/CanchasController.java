package com.canchas.mscanchas.controller;

import com.canchas.mscanchas.dto.CanchaRequestDTO;
import com.canchas.mscanchas.dto.CanchaResponseDTO;
import com.canchas.mscanchas.service.CanchaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/canchas")
public class CanchasController {

    private final CanchaService service;

    public CanchasController(CanchaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CanchaResponseDTO> crear(@Valid @RequestBody CanchaRequestDTO request) {
        CanchaResponseDTO nueva = service.crearCancha(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CanchaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanchaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}