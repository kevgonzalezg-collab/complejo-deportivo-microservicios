package com.canchas.horariosms.exception;

// excepción  negocio para topes de hora y canchas no disponibles
public class HorarioException extends RuntimeException {
    public HorarioException(String message) {
        super(message);
    }
}