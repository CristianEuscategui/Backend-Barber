package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class ResenaRequestDTO {
    private Long citaId;
    private Integer estrellas;
    private String comentario;
}