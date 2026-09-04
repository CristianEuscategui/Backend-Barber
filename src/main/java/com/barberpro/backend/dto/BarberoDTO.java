package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class BarberoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String telefono;
    private String email;
    private Boolean activo;
    private String fotoUrl;
}