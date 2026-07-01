package com.canchas.notificacionesms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificacionResponseDTO {
    private Long id;
    private String correoDestino;
    private String asunto;
    private String mensaje;
    private LocalDateTime fechaEnvio;
    private String estado;
}