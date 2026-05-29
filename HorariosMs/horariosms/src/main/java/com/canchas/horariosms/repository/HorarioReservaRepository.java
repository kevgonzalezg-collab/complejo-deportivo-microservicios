package com.canchas.horariosms.repository;

import com.canchas.horariosms.model.HorarioReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioReservaRepository extends JpaRepository<HorarioReserva, Long> {

    // Busca si ya existe una reserva para esa cancha, ese día y a esa hora
    boolean existsByCanchaIdAndFechaAndHoraInicio(Integer canchaId, LocalDate fecha, LocalTime horaInicio);

    // Permite buscar todas las reservas de un cliente específico por su RUT
    List<HorarioReserva> findByRutCliente(String rutCliente);
}