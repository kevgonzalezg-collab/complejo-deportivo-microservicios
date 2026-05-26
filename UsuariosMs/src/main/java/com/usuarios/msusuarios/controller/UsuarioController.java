package com.usuarios.msusuarios.controller;

import com.usuarios.msusuarios.dto.UsuarioRequestDTO;
import com.usuarios.msusuarios.dto.UsuarioResponseDTO;
import com.usuarios.msusuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List; // 📢 Asegúrate de tener esta importación

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO nuevoUsuario = service.crearUsuario(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // 📢 AGREGADO: Endpoint para listar todos (sirve para probar en Postman)
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    // 📢 AGREGADO CRÍTICO: Lo necesita ms-reservas para validar la existencia del usuario
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }
}