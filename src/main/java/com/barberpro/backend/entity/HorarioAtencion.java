package com.barberpro.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_atencion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class HorarioAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private DayOfWeek diaSemana;

    private LocalTime horaApertura;
    private LocalTime horaCierre;

    @Builder.Default
    private Boolean abierto = true;
}