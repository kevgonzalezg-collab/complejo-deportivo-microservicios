package com.reservas.msreservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionesEspejoDTO {
    private String correoDestino;
    private String asunto;
    private String mensaje;
    private String fechaEnvio;
    private String estado;
}