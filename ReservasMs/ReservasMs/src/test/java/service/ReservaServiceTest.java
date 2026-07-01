package service;

import com.reservas.msreservas.dto.ReservaResponseDTO;
import com.reservas.msreservas.entity.Reservas;
import com.reservas.msreservas.exception.ReservaException;
import com.reservas.msreservas.repository.ReservaRepository;
import com.reservas.msreservas.services.ReservasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservasServiceTest {

    @Mock
    private ReservaRepository repository;

    @InjectMocks
    private ReservasService service;

    @Test
    void testListarTodas() {
        // GIVEN: La base de datos simulada contiene una reserva
        Reservas reserva = new Reservas();
        reserva.setId(100L);
        reserva.setEstado("PENDIENTE");

        when(repository.findAll()).thenReturn(List.of(reserva));

        // WHEN: Consultamos el listado completo
        List<ReservaResponseDTO> resultado = service.listarTodas();

        // THEN: Validamos que devuelva el elemento y sus datos de forma correcta
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    void testObtenerPorIdExiste() {
        // GIVEN: Existe una reserva registrada con el ID 100
        Reservas reserva = new Reservas();
        reserva.setId(100L);
        reserva.setEstado("PENDIENTE");

        when(repository.findById(100L)).thenReturn(Optional.of(reserva));

        // WHEN: Buscamos la reserva por ID
        ReservaResponseDTO response = service.obtenerPorId(100L);

        // THEN: Confirmamos que retorne los datos correctos
        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("PENDIENTE", response.getEstado());
    }

    @Test
    void testObtenerPorIdNoExisteLanzaExcepcion() {
        // GIVEN: La reserva no se encuentra en XAMPP
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // WHEN & THEN: Comprobamos que dispare tu excepción personalizada 'ReservaException'
        assertThrows(ReservaException.class, () -> service.obtenerPorId(999L));
    }

    @Test
    void testActualizarEstadoReservaExitoso() {
        // GIVEN: Una reserva existente en estado PENDIENTE
        Reservas reserva = new Reservas();
        reserva.setId(100L);
        reserva.setEstado("PENDIENTE");

        when(repository.findById(100L)).thenReturn(Optional.of(reserva));
        when(repository.save(any(Reservas.class))).thenReturn(reserva);

        // WHEN: Ejecutamos el método PUT de actualización a PAGADA
        service.actualizarEstadoReserva(100L, "PAGADA");

        // THEN: Validamos que el objeto interno haya cambiado de estado exitosamente
        assertEquals("PAGADA", reserva.getEstado());
    }
}