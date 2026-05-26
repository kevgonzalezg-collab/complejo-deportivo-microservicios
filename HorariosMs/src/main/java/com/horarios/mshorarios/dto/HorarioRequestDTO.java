package com.horarios.mshorarios.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class HorarioRequestDTO {
    private Long canchaId;
    private String diaSemana; // Asegúrate de que diga esto
    private LocalTime horaInicio;
    private LocalTime horaFin;
}