package com.usuarios.msusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioException extends RuntimeException {
    public UsuarioException(String mensaje) {
        super(mensaje);
    }
}