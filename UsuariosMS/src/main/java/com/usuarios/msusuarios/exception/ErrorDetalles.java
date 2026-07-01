package com.usuarios.msusuarios.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetalles {
    private LocalDateTime timestamp;
    private int estado;
    private String error;
    private String mensaje;
}