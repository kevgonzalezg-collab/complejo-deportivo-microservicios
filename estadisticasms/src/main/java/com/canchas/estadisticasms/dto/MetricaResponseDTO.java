package com.canchas.estadisticasms.dto;

import java.time.LocalDate;

public class MetricaResponseDTO {
    private Long id;
    private LocalDate fecha;
    private Integer totalReservasCreadas;
    private Integer totalReservasPagadas;
    private Integer totalReservasCanceladas;
    private Double recaudacionTotal;

    // Constructor vacío obligatorio para Jackson
    public MetricaResponseDTO() {}

    // 🔥 Constructor con los 6 campos exactos en orden
    public MetricaResponseDTO(Long id, LocalDate fecha, Integer totalReservasCreadas,
                              Integer totalReservasPagadas, Integer totalReservasCanceladas,
                              Double recaudacionTotal) {
        this.id = id;
        this.fecha = fecha;
        this.totalReservasCreadas = totalReservasCreadas;
        this.totalReservasPagadas = totalReservasPagadas;
        this.totalReservasCanceladas = totalReservasCanceladas;
        this.recaudacionTotal = recaudacionTotal;
    }

    // Getters y Setters (O usa @Data / @Getter @Setter de Lombok)
}