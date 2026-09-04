package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class ServicioDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer duracionMinutos;
}