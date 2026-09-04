package com.barberpro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    private Integer duracionMinutos;
}