package com.canchas.horariosms.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioRequestDTO {
    private Integer canchaId;
    private String rutCliente;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}