package com.pagos.mspagos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // <-- Esto es obligatorio para que el "new" del Service funcione
public class PagoResponseDTO {
    private Long id;
    private Long reservaId;
    private Double monto;
    private String estadoPago; // O estado, pero que coincida con lo que manda el constructor
}