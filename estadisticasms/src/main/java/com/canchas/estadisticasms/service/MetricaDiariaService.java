package com.canchas.estadisticasms.service;

import com.canchas.estadisticasms.dto.MetricaRequestDTO;
import com.canchas.estadisticasms.dto.MetricaResponseDTO;
import com.canchas.estadisticasms.model.MetricaDiaria;
import com.canchas.estadisticasms.repository.MetricaDiariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetricaDiariaService {

    private final MetricaDiariaRepository repository;

    public MetricaDiariaService(MetricaDiariaRepository repository) {
        this.repository = repository;
    }

    public MetricaResponseDTO registrarOActualizar(MetricaRequestDTO dto) {
        // Buscamos si ya hay un registro para esa fecha
        Optional<MetricaDiaria> metricaExistente = repository.findByFecha(dto.getFecha());
        MetricaDiaria metrica;

        if (metricaExistente.isPresent()) {
            // Si existe, actualizamos los valores (ej. sumamos una nueva reserva)
            metrica = metricaExistente.get();
            metrica.setTotalReservas(metrica.getTotalReservas() + dto.getTotalReservas());
            metrica.setIngresosTotales(metrica.getIngresosTotales().add(dto.getIngresosTotales()));
            metrica.setCanchasActivas(dto.getCanchasActivas()); // Se mantiene la capacidad del complejo
        } else {
            // Si no existe, creamos el registro del día desde cero
            metrica = new MetricaDiaria();
            metrica.setFecha(dto.getFecha());
            metrica.setTotalReservas(dto.getTotalReservas());
            metrica.setIngresosTotales(dto.getIngresosTotales());
            metrica.setCanchasActivas(dto.getCanchasActivas());
        }

        MetricaDiaria guardada = repository.save(metrica);

        return new MetricaResponseDTO(
                guardada.getId(), guardada.getFecha(),
                guardada.getTotalReservas(), guardada.getIngresosTotales(),
                guardada.getCanchasActivas()
        );
    }

    public List<MetricaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(m -> new MetricaResponseDTO(m.getId(), m.getFecha(), m.getTotalReservas(), m.getIngresosTotales(), m.getCanchasActivas()))
                .toList();
    }
}