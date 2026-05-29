package com.canchas.horariosms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class HorarioResponseDTO {
    private Long id;
    private Integer canchaId;
    private String rutCliente;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
}