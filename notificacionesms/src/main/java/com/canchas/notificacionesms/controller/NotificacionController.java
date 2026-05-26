package com.canchas.notificacionesms.controller;

import com.canchas.notificacionesms.dto.NotificacionRequestDTO;
import com.canchas.notificacionesms.dto.NotificacionResponseDTO;
import com.canchas.notificacionesms.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService service;

    public NotificacionController(NotificacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> enviarNotificacion(@RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.enviarYRegistrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
}