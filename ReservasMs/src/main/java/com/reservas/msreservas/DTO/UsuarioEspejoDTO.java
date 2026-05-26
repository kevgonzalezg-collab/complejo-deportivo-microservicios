package com.reservas.msreservas.dto;

import lombok.Data;

@Data
public class UsuarioEspejoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
}