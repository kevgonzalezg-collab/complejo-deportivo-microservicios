package com.canchas.estadisticasms.repository;

import com.canchas.estadisticasms.model.MetricaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MetricaDiariaRepository extends JpaRepository<MetricaDiaria, Long> {

    // Spring construirá la consulta para buscar las métricas exactas de una fecha
    Optional<MetricaDiaria> findByFecha(LocalDate fecha);
}