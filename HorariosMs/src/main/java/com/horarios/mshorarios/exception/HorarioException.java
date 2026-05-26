package com.horarios.mshorarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Mantenemos el estatus 404 porque significa que el horario no existe
@ResponseStatus(HttpStatus.NOT_FOUND)
public class HorarioException extends RuntimeException {
    public HorarioException(String mensaje) {
        super(mensaje);
    }
}