package com.canchas.listanegrams.dto;

import lombok.Data;

@Data
public class ClienteBloqueadoRequestDTO {
    private String rut;
    private String nombre;
    private String motivo;
}