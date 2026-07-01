package com.reservas.msreservas.controller;

import com.reservas.msreservas.dto.ReservaRequestDTO;
import com.reservas.msreservas.dto.ReservaResponseDTO;
import com.reservas.msreservas.services.ReservasService;
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
    public ResponseEntity<ReservaResponseDTO> crear(
            @RequestBody ReservaRequestDTO request,
            @RequestHeader("Authorization") String token
    ) {

        ReservaResponseDTO nuevaReserva = service.crearReserva(request, token);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }


    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado
    ) {
        service.actualizarEstadoReserva(id, nuevoEstado);
        return ResponseEntity.ok("Estado de la reserva #" + id + " actualizado a: " + nuevoEstado);
    }
}