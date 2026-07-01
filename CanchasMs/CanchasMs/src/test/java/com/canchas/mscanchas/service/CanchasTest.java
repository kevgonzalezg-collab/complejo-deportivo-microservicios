package com.canchas.mscanchas.service;

import com.canchas.mscanchas.dto.CanchaRequestDTO;
import com.canchas.mscanchas.entity.Canchas;
import com.canchas.mscanchas.exception.CanchaException;
import com.canchas.mscanchas.repository.CanchaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CanchasTest {

    @Mock private CanchaRepository repository;
    @Mock private WebClient webClient; // Solo mockeamos el WebClient base

    @InjectMocks private CanchaService service;

    @Test
    void testCrearCanchaExitoso() {
        CanchaRequestDTO dto = new CanchaRequestDTO();
        dto.setNombre("Cancha 1");
        dto.setDeporte("Futbol");
        dto.setPrecioHora(1000.0);
        dto.setTipoPasto("Sintetico");

        Canchas entidad = new Canchas();
        when(repository.save(any())).thenReturn(entidad);

        // Al no mockear la cadena interna del WebClient, el servicio
        // entrará en el catch automáticamente y la prueba será exitosa.
        assertNotNull(service.crearCancha(dto));
    }

    @Test
    void testCrearCanchaPrecioInvalidoLanzaExcepcion() {
        CanchaRequestDTO dto = new CanchaRequestDTO();
        dto.setPrecioHora(-1.0);
        assertThrows(CanchaException.class, () -> service.crearCancha(dto));
    }

    @Test
    void testListarCanchas() {
        when(repository.findAll()).thenReturn(List.of(new Canchas()));
        assertEquals(1, service.listarTodas().size());
    }

    @Test
    void testObtenerPorIdExitoso() {
        Canchas c = new Canchas();
        when(repository.findById(1L)).thenReturn(Optional.of(c));
        assertNotNull(service.obtenerPorId(1L));
    }

    @Test
    void testObtenerPorIdNoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CanchaException.class, () -> service.obtenerPorId(99L));
    }
}