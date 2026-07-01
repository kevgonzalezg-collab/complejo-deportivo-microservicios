package com.canchas.mscanchas.exception;

// Esta es la "bomba" que lanzaremos cuando algo falle
public class CanchaException extends RuntimeException {
    public CanchaException(String mensaje) {
        super(mensaje);
    }
}