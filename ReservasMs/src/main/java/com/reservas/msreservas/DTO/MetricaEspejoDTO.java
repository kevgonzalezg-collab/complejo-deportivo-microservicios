package com.reservas.msreservas.dto; // El paquete de DTOs en tu proyecto de Reservas

import java.time.LocalDate;

public class MetricaEspejoDTO {

    private LocalDate fecha;
    private Integer totalReservasCreadas;
    private Integer totalReservasPagadas;
    private Integer totalReservasCanceladas;
    private Double recaudacionTotal;

    // 1. Constructor vacío obligatorio (Jackson lo usa para empaquetar el JSON)
    public MetricaEspejoDTO() {
    }

    // 2. Constructor completo (Te servirá para instanciarlo rápido con un 'new')
    public MetricaEspejoDTO(LocalDate fecha, Integer totalReservasCreadas,
                            Integer totalReservasPagadas, Integer totalReservasCanceladas,
                            Double recaudacionTotal) {
        this.fecha = fecha;
        this.totalReservasCreadas = totalReservasCreadas;
        this.totalReservasPagadas = totalReservasPagadas;
        this.totalReservasCanceladas = totalReservasCanceladas;
        this.recaudacionTotal = recaudacionTotal;
    }

    // 3. GETTERS Y SETTERS (Obligatorios para que RestTemplate pueda leer los datos)
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