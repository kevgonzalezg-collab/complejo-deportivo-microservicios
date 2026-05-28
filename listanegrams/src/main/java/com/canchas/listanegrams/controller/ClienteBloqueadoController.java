package com.canchas.listanegrams.controller;

import com.canchas.listanegrams.dto.ClienteBloqueadoRequestDTO;
import com.canchas.listanegrams.dto.ClienteBloqueadoResponseDTO;
import com.canchas.listanegrams.service.ClienteBloqueadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteBloqueadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    //  Recibe el RUT dinámicamente y soluciona el 404
    @GetMapping("/{rut}")
    public ResponseEntity<?> obtenerPorRut(@PathVariable String rut) {
        Optional<ClienteBloqueadoResponseDTO> cliente = service.obtenerPorRut(rut);

        if (cliente.isPresent()) {

            return ResponseEntity.ok(java.util.Map.of(
                    "bloqueado", true,
                    "mensaje", "Acceso denegado: El cliente se encuentra en la lista negra.",
                    "datos", cliente.get()
            ));
        }


        return ResponseEntity.ok(java.util.Map.of(
                "bloqueado", false,
                "mensaje", "Cliente limpio. Permitido reservar."
        ));
    }
}