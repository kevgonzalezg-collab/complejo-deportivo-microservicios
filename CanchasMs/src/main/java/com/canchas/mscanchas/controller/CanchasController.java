package com.canchas.mscanchas.controller;

import com.canchas.mscanchas.dto.CanchaRequestDTO;
import com.canchas.mscanchas.dto.CanchaResponseDTO;
import com.canchas.mscanchas.service.CanchaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canchas")
public class CanchasController {

    @Autowired
    private CanchaService service;

    @PostMapping
    public ResponseEntity<CanchaResponseDTO> crear(@Valid @RequestBody CanchaRequestDTO dto) {
        return new ResponseEntity<>(service.crearCancha(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CanchaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanchaResponseDTO> obtener(@PathVariable Long id) {
        // Si no existe, el Service lanzará CanchaException y el Handler responderá el error
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}