package com.usuarios.msusuarios.dto;

import lombok.Data;

@Data
public class NotificacionesEspejoDTO {
    private String correoDestino;
    private String asunto;
    private String mensaje;
}