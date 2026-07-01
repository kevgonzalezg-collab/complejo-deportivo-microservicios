package com.usuarios.msusuarios.dto;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String email;
    private String rol;
    private String telefono;
}