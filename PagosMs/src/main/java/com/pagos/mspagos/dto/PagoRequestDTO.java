package com.pagos.mspagos.dto;

import lombok.Data;

@Data
public class PagoRequestDTO {
    private Long reservaId;
    private Double monto;
    private String metodoPago; // Ejemplo: "DEBITO", "CREDITO", "TRANSFERENCIA"
}