package com.reservas.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguridadEspejoDTO {
    private boolean valido;
    private String email;
    private String rol;
}