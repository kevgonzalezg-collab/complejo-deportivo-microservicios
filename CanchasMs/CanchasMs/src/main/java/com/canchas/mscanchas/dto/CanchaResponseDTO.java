package com.canchas.mscanchas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CanchaResponseDTO {
    private Long id;
    private String nombre;
    private String deporte;
    private Double precioHora;
    private String tipoPasto;
}