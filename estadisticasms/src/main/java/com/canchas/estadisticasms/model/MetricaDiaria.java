package com.canchas.estadisticasms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "metrica_diaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricaDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate fecha;

    @Column(name = "total_reservas", nullable = false)
    private Integer totalReservas;

    @Column(name = "ingresos_totales", nullable = false)
    private BigDecimal ingresosTotales;

    @Column(name = "canchas_activas", nullable = false)
    private Integer canchasActivas;
}