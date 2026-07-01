package com.canchas.inventarioms.service;

import com.canchas.inventarioms.dto.ImplementoRequestDTO;
import com.canchas.inventarioms.model.Implemento;
import com.canchas.inventarioms.repository.ImplementoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImplementoServiceTest {

    @Mock private ImplementoRepository repository;
    @Mock private WebClient estadisticasWebClient;

    @InjectMocks private ImplementoService service;

    @Test
    void testCrearImplementoExitoso() {
        ImplementoRequestDTO dto = new ImplementoRequestDTO();
        dto.setNombre("Balón de Fútbol");
        dto.setCantidad(10);
        dto.setEstado("DISPONIBLE");

        Implemento guardado = new Implemento(1L, "Balón de Fútbol", 10, "DISPONIBLE");

        when(repository.save(any())).thenReturn(guardado);

        // El WebClient no configurado lanzará un NPE capturado por el try-catch del servicio
        assertNotNull(service.crear(dto));
    }

    @Test
    void testListarImplementos() {
        when(repository.findAll()).thenReturn(List.of(
                new Implemento(1L, "Balón", 5, "DISPONIBLE"),
                new Implemento(2L, "Red", 1, "BUENO")
        ));

        assertEquals(2, service.listar().size());
    }

    @Test
    void testListarVacio() {
        when(repository.findAll()).thenReturn(List.of());
        assertEquals(0, service.listar().size());
    }

    @Test
    void testCrearImplementoMantieneNombre() {
        ImplementoRequestDTO dto = new ImplementoRequestDTO();
        dto.setNombre("Cono");

        Implemento guardado = new Implemento(1L, "Cono", 20, "NUEVO");
        when(repository.save(any())).thenReturn(guardado);

        assertEquals("Cono", service.crear(dto).getNombre());
    }

    @Test
    void testCrearImplementoEstadoCorrecto() {
        ImplementoRequestDTO dto = new ImplementoRequestDTO();
        dto.setEstado("MANTENCION");

        Implemento guardado = new Implemento(1L, "Malla", 2, "MANTENCION");
        when(repository.save(any())).thenReturn(guardado);

        assertEquals("MANTENCION", service.crear(dto).getEstado());
    }
}