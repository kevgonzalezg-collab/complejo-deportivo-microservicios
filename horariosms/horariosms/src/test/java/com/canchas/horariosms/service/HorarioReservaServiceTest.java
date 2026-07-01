package com.canchas.horariosms.service;

import com.canchas.horariosms.dto.HorarioRequestDTO;
import com.canchas.horariosms.exception.HorarioException;
import com.canchas.horariosms.model.HorarioReserva;
import com.canchas.horariosms.repository.HorarioReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HorarioReservaServiceTest {

    @Mock
    private HorarioReservaRepository repository;

    @InjectMocks
    private HorarioReservaService service;

    @Test
    void testCrearReservaExitoso() {
        HorarioRequestDTO dto = new HorarioRequestDTO();
        dto.setCanchaId(1); // Usamos 1 en lugar de 1L
        dto.setRutCliente("11111111-1");
        dto.setFecha(LocalDate.now());
        dto.setHoraInicio(LocalTime.of(10, 0));

        when(repository.existsByCanchaIdAndFechaAndHoraInicio(any(), any(), any())).thenReturn(false);

        HorarioReserva reserva = new HorarioReserva();
        reserva.setEstado("CONFIRMADA");
        when(repository.save(any())).thenReturn(reserva);

        assertNotNull(service.crearReserva(dto));
    }

    @Test
    void testCrearReservaLanzaExcepcionSiYaExiste() {
        HorarioRequestDTO dto = new HorarioRequestDTO();
        when(repository.existsByCanchaIdAndFechaAndHoraInicio(any(), any(), any())).thenReturn(true);

        assertThrows(HorarioException.class, () -> service.crearReserva(dto));
    }

    @Test
    void testListarPorClienteExitoso() {
        when(repository.findByRutCliente("11111111-1")).thenReturn(List.of(new HorarioReserva()));
        assertEquals(1, service.listarPorCliente("11111111-1").size());
    }

    @Test
    void testListarPorClienteVacio() {
        when(repository.findByRutCliente("99999999-9")).thenReturn(List.of());
        assertEquals(0, service.listarPorCliente("99999999-9").size());
    }

    @Test
    void testCrearReservaEstadoConfirmada() {
        HorarioRequestDTO dto = new HorarioRequestDTO();
        when(repository.existsByCanchaIdAndFechaAndHoraInicio(any(), any(), any())).thenReturn(false);

        HorarioReserva reserva = new HorarioReserva();
        reserva.setEstado("CONFIRMADA");
        when(repository.save(any())).thenReturn(reserva);

        assertEquals("CONFIRMADA", service.crearReserva(dto).getEstado());
    }
}