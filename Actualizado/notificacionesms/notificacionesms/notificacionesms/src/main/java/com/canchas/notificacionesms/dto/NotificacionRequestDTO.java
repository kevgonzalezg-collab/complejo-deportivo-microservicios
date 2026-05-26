package com.canchas.notificacionesms.dto;

import lombok.Data;

@Data
public class NotificacionRequestDTO {
    private String correoDestino;
    private String asunto;
    private String mensaje;
}