package com.reservas.msreservas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaRequestDTO {

    private Long idUsuario;
    private Long idCancha;
    private LocalDateTime fechaHora;
    private Integer duracionMinutos;
}