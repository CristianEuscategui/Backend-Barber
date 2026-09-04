package com.barberpro.backend.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaRequestDTO {
    private String clienteNombre;
    private String clienteTelefono;
    private Long barberoId;
    private Long servicioId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime hora;

    private String notas;
}