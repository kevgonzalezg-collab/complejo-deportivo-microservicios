package com.canchas.estadisticasms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "metrica_diaria")
public class MetricaDiaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Column(name = "total_reservas_creadas")
    private Integer totalReservasCreadas;

    @Column(name = "total_reservas_pagadas")
    private Integer totalReservasPagadas;

    @Column(name = "total_reservas_canceladas")
    private Integer totalReservasCanceladas;

    @Column(name = "recaudacion_total")
    private Double recaudacionTotal;

    // --- CONSTRUCTORES ---
    public MetricaDiaria() {
    }

    // --- GETTERS Y SETTERS COMPLETOS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getTotalReservasCreadas() {
        return totalReservasCreadas;
    }

    public void setTotalReservasCreadas(Integer totalReservasCreadas) {
        this.totalReservasCreadas = totalReservasCreadas;
    }

    public Integer getTotalReservasPagadas() {
        return totalReservasPagadas;
    }

    public void setTotalReservasPagadas(Integer totalReservasPagadas) {
        this.totalReservasPagadas = totalReservasPagadas;
    }

    public Integer getTotalReservasCanceladas() {
        return totalReservasCanceladas;
    }

    public void setTotalReservasCanceladas(Integer totalReservasCanceladas) {
        this.totalReservasCanceladas = totalReservasCanceladas;
    }

    public Double getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(Double recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }
}