package com.canchas.inventarioms.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MetricaEspejoDTO {
    private LocalDate fecha;
    private int totalReservasCreadas;
    private int totalReservasPagadas;
    private int totalReservasCanceladas;
    private double recaudacionTotal;
}