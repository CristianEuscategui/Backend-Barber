package com.barberpro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    private String clienteNombre;

    private Integer estrellas; // 1 a 5

    @Column(length = 500)
    private String comentario;

    private LocalDateTime fecha;

    @Builder.Default
    private Boolean verificada = true;
}