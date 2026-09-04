package com.barberpro.backend.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String usuario;
    private String password;
}