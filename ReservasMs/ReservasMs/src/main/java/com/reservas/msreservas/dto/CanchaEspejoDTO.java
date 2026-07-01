package com.reservas.msreservas.dto;

import lombok.Data;

@Data
public class CanchaEspejoDTO {
    private Long id;
    private String nombre;
    private String deporte;
    private Double precioHora;
    private String tipoPasto;
}