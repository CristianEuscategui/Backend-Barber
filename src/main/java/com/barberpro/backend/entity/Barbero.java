package com.barberpro.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "barberos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String email;
    private Boolean activo;
    private String fotoUrl;

}