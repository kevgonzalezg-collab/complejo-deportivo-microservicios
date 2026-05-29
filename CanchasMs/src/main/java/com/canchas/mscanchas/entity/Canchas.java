package com.canchas.mscanchas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "canchas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Canchas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String deporte; // Ejemplo: "Fútbol", "Tenis", "Padel"
    private Double precioHora;
    private String tipoPasto; // Ejemplo: "Sintético", "Natural"
}