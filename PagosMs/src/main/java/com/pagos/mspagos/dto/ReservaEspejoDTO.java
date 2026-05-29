package com.pagos.mspagos.dto;

import lombok.Data;

@Data
public class ReservaEspejoDTO {
    private Long id;
    private Long usuarioId;
    private Long canchaId;
    private Double total;
    private String estado;
}