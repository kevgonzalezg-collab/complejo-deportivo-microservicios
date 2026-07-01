package com.reservas.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponseDTO {
    private Long id;
    private Long idUsuario;
    private Long idCancha;
    private LocalDateTime fechaHora;
    private String estado;
}