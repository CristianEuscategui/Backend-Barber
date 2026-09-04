package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class RecuperarPasswordDTO {
    private String usuario;
    private String respuestaSeguridad;
    private String nuevaPassword;
}