package com.canchas.listanegrams.service;

import com.canchas.listanegrams.dto.ClienteBloqueadoRequestDTO;
import com.canchas.listanegrams.dto.ClienteBloqueadoResponseDTO;
import com.canchas.listanegrams.model.ClienteBloqueado;
import com.canchas.listanegrams.repository.ClienteBloqueadoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteBloqueadoServiceTest {

    @Mock private ClienteBloqueadoRepository repository;

    @InjectMocks private ClienteBloqueadoService service;

    @Test
    void testBloquearExitoso() {
        ClienteBloqueadoRequestDTO dto = new ClienteBloqueadoRequestDTO();
        dto.setRut("11111111-1");
        dto.setNombre("Cliente Prueba");
        dto.setMotivo("Incumplimiento");

        when(repository.findByRut("11111111-1")).thenReturn(Optional.empty());

        ClienteBloqueado cliente = new ClienteBloqueado();
        cliente.setRut("11111111-1");
        when(repository.save(any())).thenReturn(cliente);

        ClienteBloqueadoResponseDTO response = service.bloquear(dto);
        assertNotNull(response);
    }

    @Test
    void testBloquearLanzaExcepcionSiYaExiste() {
        ClienteBloqueadoRequestDTO dto = new ClienteBloqueadoRequestDTO();
        dto.setRut("11111111-1");

        when(repository.findByRut("11111111-1")).thenReturn(Optional.of(new ClienteBloqueado()));

        assertThrows(RuntimeException.class, () -> service.bloquear(dto));
    }

    @Test
    void testListarTodos() {
        when(repository.findAll()).thenReturn(List.of(new ClienteBloqueado()));
        assertEquals(1, service.listarTodos().size());
    }

    @Test
    void testObtenerPorRutExiste() {
        ClienteBloqueado c = new ClienteBloqueado();
        c.setRut("11111111-1");
        when(repository.findByRut("11111111-1")).thenReturn(Optional.of(c));

        assertTrue(service.obtenerPorRut("11111111-1").isPresent());
    }

    @Test
    void testObtenerPorRutNoExiste() {
        when(repository.findByRut("00000000-0")).thenReturn(Optional.empty());

        assertFalse(service.obtenerPorRut("00000000-0").isPresent());
    }
}