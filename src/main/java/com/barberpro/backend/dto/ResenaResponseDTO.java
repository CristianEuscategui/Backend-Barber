package com.barberpro.backend.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ResenaResponseDTO {
    private Long id;
    private String clienteNombre;
    private Long barberoId;
    private String barberoNombre;
    private Integer estrellas;
    private String comentario;
    private LocalDateTime fecha;
    private Boolean verificada;
}