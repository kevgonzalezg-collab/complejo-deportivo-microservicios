package com.reservas.msreservas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // (Fechas pasadas, etc.)
    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<ErrorDetalles> manejarReservaException(ReservaException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en Reservas",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // microservicio externo responde con un error HTTP (404, 500, 401, etc.)
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorDetalles> manejarWebClientResponseException(WebClientResponseException ex) {
        String mensajeDetallado = String.format("El microservicio en la URL [%s] falló. Servidor externo respondió con Código HTTP: %d (%s). Detalles: %s",
                ex.getRequest() != null ? ex.getRequest().getURI() : "Desconocida",
                ex.getStatusCode().value(),
                ex.getStatusText(),
                ex.getResponseBodyAsString()
        );

        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Integración (WebClient)",
                mensajeDetallado
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // microservicio externo está APAGADO
    @ExceptionHandler(java.net.ConnectException.class)
    public ResponseEntity<ErrorDetalles> manejarConexionRechazada(java.net.ConnectException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Red Interna",
                "No se pudo establecer conexión: Uno de los microservicios externos configurados en WebClientConfig está APAGADO o inaccesible en IntelliJ."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //  JSON vacío
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarErroresGlobales(Exception ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno Inesperado",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}