package com.canchas.horariosms.controller;

import com.canchas.horariosms.dto.HorarioRequestDTO;
import com.canchas.horariosms.dto.HorarioResponseDTO;
import com.canchas.horariosms.service.HorarioReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioReservaController {

    private final HorarioReservaService service;

    public HorarioReservaController(HorarioReservaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> reservar(@RequestBody HorarioRequestDTO dto) {
        try {
            HorarioResponseDTO response = service.crearReserva(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{rut}")
    public ResponseEntity<List<HorarioResponseDTO>> listarPorCliente(@PathVariable String rut) {
        return ResponseEntity.ok(service.listarPorCliente(rut));
    }
}