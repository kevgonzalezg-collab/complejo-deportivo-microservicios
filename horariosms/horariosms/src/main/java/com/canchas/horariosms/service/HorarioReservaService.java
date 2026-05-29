package com.canchas.horariosms.service;

import com.canchas.horariosms.dto.HorarioRequestDTO;
import com.canchas.horariosms.dto.HorarioResponseDTO;
import com.canchas.horariosms.exception.HorarioException; // 🎯 Importamos tu nueva excepción
import com.canchas.horariosms.model.HorarioReserva;
import com.canchas.horariosms.repository.HorarioReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioReservaService {

    private final HorarioReservaRepository repository;

    public HorarioReservaService(HorarioReservaRepository repository) {
        this.repository = repository;
    }

    public HorarioResponseDTO crearReserva(HorarioRequestDTO dto) {
        // HorarioException
        if (repository.existsByCanchaIdAndFechaAndHoraInicio(dto.getCanchaId(), dto.getFecha(), dto.getHoraInicio())) {
            throw new HorarioException("No se puede habilitar el bloque: La cancha ya está reservada en ese horario.");
        }

        HorarioReserva reserva = new HorarioReserva();
        reserva.setCanchaId(dto.getCanchaId());
        reserva.setRutCliente(dto.getRutCliente());
        reserva.setFecha(dto.getFecha());
        reserva.setHoraInicio(dto.getHoraInicio());
        reserva.setHoraFin(dto.getHoraFin());
        reserva.setEstado("CONFIRMADA");

        HorarioReserva guardada = repository.save(reserva);

        return new HorarioResponseDTO(
                guardada.getId(), guardada.getCanchaId(), guardada.getRutCliente(),
                guardada.getFecha(), guardada.getHoraInicio(), guardada.getHoraFin(), guardada.getEstado()
        );
    }

    public List<HorarioResponseDTO> listarPorCliente(String rut) {
        return repository.findByRutCliente(rut).stream()
                .map(r -> new HorarioResponseDTO(r.getId(), r.getCanchaId(), r.getRutCliente(),
                        r.getFecha(), r.getHoraInicio(), r.getHoraFin(), r.getEstado()))
                .toList();
    }
}