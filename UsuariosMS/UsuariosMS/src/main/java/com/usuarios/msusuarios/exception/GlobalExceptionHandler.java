package com.usuarios.msusuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorDetalles> manejarUsuarioException(UsuarioException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Error en Microservicio Usuarios",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarErrorGlobal(Exception ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno",
                // 📢 CAMBIO CRÍTICO: Aquí sacamos el texto fijo y ponemos el error real
                ex.getMessage() != null ? ex.getMessage() : ex.toString()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}