package com.canchas.mscanchas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetalles {
    private LocalDateTime timestamp;
    private int estado;
    private String error;
    private String mensaje;
}