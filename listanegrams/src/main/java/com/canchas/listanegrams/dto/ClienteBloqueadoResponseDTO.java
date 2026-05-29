package com.canchas.listanegrams.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ClienteBloqueadoResponseDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String motivo;
    private LocalDate fechaBloqueo;
}