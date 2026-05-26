package com.canchas.mscanchas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CanchaRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El deporte es obligatorio")
    private String deporte;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double precioHora;

    private String tipoPasto;
}