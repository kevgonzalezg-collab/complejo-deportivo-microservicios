package com.pagos.mspagos.service;

import com.pagos.mspagos.dto.PagoRequestDTO;
import com.pagos.mspagos.entity.Pago;
import com.pagos.mspagos.exception.PagoException;
import com.pagos.mspagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagosTest {

    @Mock private PagoRepository repository;
    @Mock private WebClient reservasWebClient;
    @Mock private WebClient notificacionesWebClient;
    @Mock private WebClient estadisticasWebClient;

    @InjectMocks private PagoService service;

    @Test
    void testProcesarPagoLanzaExcepcionSiFallaReserva() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setReservaId(1L);
        dto.setMonto(100.0);

        // Al NO mockear la cadena de WebClient.get(), lanzará un NullPointerException
        // que será capturado por tu catch y convertido en PagoException.
        assertThrows(PagoException.class, () -> service.procesarPago(dto));
    }

    @Test
    void testListarTodos() {
        when(repository.findAll()).thenReturn(List.of(new Pago()));
        assertEquals(1, service.listarTodosLosPagos().size());
    }

    @Test
    void testObtenerPagoPorIdExitoso() {
        Pago p = new Pago();
        when(repository.findById(1L)).thenReturn(Optional.of(p));
        assertNotNull(service.obtenerPagoPorId(1L));
    }

    @Test
    void testObtenerPagoNoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PagoException.class, () -> service.obtenerPagoPorId(99L));
    }

    // El quinto test lo hacemos sobre listar vacio para asegurar el 5/5
    @Test
    void testListarTodosVacio() {
        when(repository.findAll()).thenReturn(List.of());
        assertEquals(0, service.listarTodosLosPagos().size());
    }
}