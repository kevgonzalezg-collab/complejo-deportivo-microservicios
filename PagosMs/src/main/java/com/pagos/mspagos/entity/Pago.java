package com.pagos.mspagos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reserva_id", nullable = false)
    private Long reservaId;

    @Column(nullable = false)
    private Double monto;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    @Column(name = "estado_pago")
    private String estadoPago = "PENDIENTE";

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;


    @PrePersist
    protected void onCreate() {
        this.fechaPago = LocalDateTime.now();
    }
}