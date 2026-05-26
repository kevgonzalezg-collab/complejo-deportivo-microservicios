package com.reservas.msreservas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<ErrorDetalles> manejarReservaException(ReservaException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en Lógica de Reservas",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarErroresGlobales(Exception ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno del Servidor",
                "Ocurrió un error inesperado: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}