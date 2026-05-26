package com.reservas.msreservas.controller;

import com.reservas.msreservas.dto.ReservaRequestDTO;
import com.reservas.msreservas.dto.ReservaResponseDTO;
import com.reservas.msreservas.service.ReservasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservasController {

    @Autowired
    private ReservasService service;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@RequestBody ReservaRequestDTO dto) {
        return new ResponseEntity<>(service.crearReserva(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
}