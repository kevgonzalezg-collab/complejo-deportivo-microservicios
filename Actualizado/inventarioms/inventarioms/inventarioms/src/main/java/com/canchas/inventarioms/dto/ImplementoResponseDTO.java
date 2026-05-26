package com.canchas.inventarioms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImplementoResponseDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private String estado;
}