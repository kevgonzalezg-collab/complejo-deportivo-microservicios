package com.canchas.estadisticasms.dto;

import java.time.LocalDate;

public class MetricaRequestDTO {

    private LocalDate fecha;
    private Integer totalReservasCreadas;
    private Integer totalReservasPagadas;
    private Integer totalReservasCanceladas;
    private Double recaudacionTotal;

    // Constructor vacío obligatorio
    public MetricaRequestDTO() {
    }

    // --- GETTERS Y SETTERS (Aquí es donde se repara el "cannot find symbol") ---

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