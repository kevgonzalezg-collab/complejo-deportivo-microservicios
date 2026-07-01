package com.reservas.msreservas.dto;

import lombok.Data;

@Data
public class ListaNegraEspejoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String motivo;
    private String fechaBloqueo;
}