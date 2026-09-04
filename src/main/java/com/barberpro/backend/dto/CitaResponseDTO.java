package com.barberpro.backend.dto;

import com.barberpro.backend.entity.EstadoCita;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponseDTO {
    private Long id;
    private String clienteNombre;
    private String clienteTelefono;
    private String barberoNombre;
    private String servicioNombre;
    private Double precio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;

    private EstadoCita estado;
    private String notas;
    private String whatsappUrl; //
}