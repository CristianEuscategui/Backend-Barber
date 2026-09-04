package com.barberpro.backend.dto;

import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class HorarioAtencionDTO {
    private DayOfWeek diaSemana;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private Boolean abierto;
}