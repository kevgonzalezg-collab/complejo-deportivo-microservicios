package com.reservas.msreservas.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;
    private Long idCancha;
    private LocalDateTime fechaHora;
    private Integer duracionMinutos;
    private String estado;
}