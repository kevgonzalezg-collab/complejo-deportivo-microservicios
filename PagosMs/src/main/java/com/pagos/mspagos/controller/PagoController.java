package com.pagos.mspagos.controller;

import com.pagos.mspagos.dto.PagoRequestDTO;
import com.pagos.mspagos.dto.PagoResponseDTO;
import com.pagos.mspagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService service;


    public PagoController(PagoService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable Long id) {
        PagoResponseDTO response = service.obtenerPagoPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listarTodos() {
        List<PagoResponseDTO> pagos = service.listarTodosLosPagos();
        return new ResponseEntity<>(pagos, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<PagoResponseDTO> registrarPago(@Valid @RequestBody PagoRequestDTO request) {
        PagoResponseDTO response = service.procesarPago(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}