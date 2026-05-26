package com.horarios.mshorarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "horarios")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long canchaId;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private boolean estaDisponible;
}