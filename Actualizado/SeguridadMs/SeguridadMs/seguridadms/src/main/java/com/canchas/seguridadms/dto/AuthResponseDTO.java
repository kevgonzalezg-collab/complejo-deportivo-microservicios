package com.canchas.seguridadms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String mensaje;
    private String token; // Aquí guardaremos el "pase" de seguridad más adelante
}