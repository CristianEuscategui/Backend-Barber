package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class CambiarCredencialesDTO {
    private String usuarioActual;
    private String passwordActual;
    private String nuevoUsuario;
    private String nuevaPassword;
    private String preguntaSeguridad;
    private String respuestaSeguridad;
}