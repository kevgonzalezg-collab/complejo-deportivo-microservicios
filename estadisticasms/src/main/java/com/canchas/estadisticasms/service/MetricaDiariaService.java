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
            // Suma los valores usando los nombres reales del script
            metrica = metricaExistente.get();
            metrica.setTotalReservasCreadas(metrica.getTotalReservasCreadas() + dto.getTotalReservasCreadas());
            metrica.setTotalReservasPagadas(metrica.getTotalReservasPagadas() + dto.getTotalReservasPagadas());
            metrica.setTotalReservasCanceladas(metrica.getTotalReservasCanceladas() + dto.getTotalReservasCanceladas());
            metrica.setRecaudacionTotal(metrica.getRecaudacionTotal() + dto.getRecaudacionTotal());
        } else {
            //Crea el registro mapeando los campos de Flyway
            metrica = new MetricaDiaria();
            metrica.setFecha(dto.getFecha());
            metrica.setTotalReservasCreadas(dto.getTotalReservasCreadas());
            metrica.setTotalReservasPagadas(dto.getTotalReservasPagadas());
            metrica.setTotalReservasCanceladas(dto.getTotalReservasCanceladas());
            metrica.setRecaudacionTotal(dto.getRecaudacionTotal());
        }

        MetricaDiaria guardada = repository.save(metrica);

        return new MetricaResponseDTO(
                guardada.getId(),
                guardada.getFecha(),
                guardada.getTotalReservasCreadas(),
                guardada.getTotalReservasPagadas(),
                guardada.getTotalReservasCanceladas(),
                guardada.getRecaudacionTotal()
        );
    }

    public List<MetricaResponseDTO> listarTodas() {
        // Retorna el DTO con la estructura real de 4 columnas de control
        return repository.findAll().stream()
                .map(m -> new MetricaResponseDTO(
                        m.getId(),
                        m.getFecha(),
                        m.getTotalReservasCreadas(),
                        m.getTotalReservasPagadas(),
                        m.getTotalReservasCanceladas(),
                        m.getRecaudacionTotal()))
                .toList();
    }
}