package com.canchas.listanegrams.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cliente_bloqueado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteBloqueado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String motivo;

    @Column(name = "fecha_bloqueo", nullable = false)
    private LocalDate fechaBloqueo;
}