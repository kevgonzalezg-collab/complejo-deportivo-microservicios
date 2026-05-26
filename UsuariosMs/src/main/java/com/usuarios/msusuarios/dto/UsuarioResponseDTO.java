package com.usuarios.msusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Esto crea el constructor con todos los parámetros automáticamente
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String email;
    private String nombre;
    private String rol;
    private String telefono;
}