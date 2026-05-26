package com.horarios.mshorarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor  // Genera el constructor sin argumentos (el que tienes ahora)
@AllArgsConstructor // <-- ESTA ES LA ANOTACIÓN QUE TE FALTA (Genera el constructor con los 5 datos)
public class HorarioResponseDTO {

    private Long id;
    private Long canchaId;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean estaDisponible; // Revisa si en tu DTO se llama "estaDisponible" o "disponible"
}