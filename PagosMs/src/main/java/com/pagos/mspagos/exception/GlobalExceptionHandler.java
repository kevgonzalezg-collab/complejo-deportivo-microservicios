package com.pagos.mspagos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //  lógica de negocio de Pagos
    @ExceptionHandler(PagoException.class)
    public ResponseEntity<ErrorDetalles> manejarPagoException(PagoException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en Microservicio Pagos",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ERRORES HTTP DE WEBCLIENT un error (404, 500, etc.)
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
                "Error de Integración (WebClient) en Pagos",
                mensajeDetallado
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. 🔌 CAPTURA CONEXIÓN RECHAZADA: Cuando intentas pagar pero el microservicio de Reservas o Notificaciones está APAGADO en IntelliJ
    @ExceptionHandler(java.net.ConnectException.class)
    public ResponseEntity<ErrorDetalles> manejarConexionRechazada(java.net.ConnectException ex) {
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Red Interna en Pagos",
                "No se pudo establecer conexión: Uno de los microservicios externos configurados en WebClientConfig (Reservas/Notificaciones/Estadísticas) está APAGADO o inaccesible."
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // respuestas vacías
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarErrorGlobal(Exception ex) {
        ex.printStackTrace();
        ErrorDetalles error = new ErrorDetalles(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno Inesperado en Pagos (" + ex.getClass().getSimpleName() + ")",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}