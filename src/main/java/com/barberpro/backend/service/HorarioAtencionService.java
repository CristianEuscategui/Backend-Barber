package com.barberpro.backend.service;

import com.barberpro.backend.dto.HorarioAtencionDTO;
import com.barberpro.backend.entity.HorarioAtencion;
import com.barberpro.backend.repository.HorarioAtencionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioAtencionService {

    private final HorarioAtencionRepository horarioRepository;

    public List<HorarioAtencion> listarTodos() {
        return horarioRepository.findAllByOrderByDiaSemanaAsc();
    }

    public HorarioAtencion actualizar(DayOfWeek dia, HorarioAtencionDTO dto) {
        HorarioAtencion horario = horarioRepository.findByDiaSemana(dia)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado para ese día"));

        horario.setAbierto(dto.getAbierto());
        if (Boolean.TRUE.equals(dto.getAbierto())) {
            if (dto.getHoraApertura() == null || dto.getHoraCierre() == null) {
                throw new RuntimeException("Debes indicar hora de apertura y cierre si el día está abierto.");
            }
            if (!dto.getHoraApertura().isBefore(dto.getHoraCierre())) {
                throw new RuntimeException("La hora de apertura debe ser antes que la hora de cierre.");
            }
            horario.setHoraApertura(dto.getHoraApertura());
            horario.setHoraCierre(dto.getHoraCierre());
        }

        return horarioRepository.save(horario);
    }

    public void validarDentroDeHorario(LocalDateTime fechaHora) {
        DayOfWeek dia = fechaHora.getDayOfWeek();
        HorarioAtencion horario = horarioRepository.findByDiaSemana(dia)
                .orElseThrow(() -> new RuntimeException("No hay horario configurado para ese día."));

        if (!Boolean.TRUE.equals(horario.getAbierto())) {
            throw new RuntimeException("El negocio está cerrado ese día. Por favor elige otra fecha.");
        }

        java.time.LocalTime hora = fechaHora.toLocalTime();
        if (hora.isBefore(horario.getHoraApertura()) || hora.isAfter(horario.getHoraCierre())) {
            throw new RuntimeException(String.format(
                "El horario de atención ese día es de %s a %s. Por favor elige una hora dentro de ese rango.",
                horario.getHoraApertura(), horario.getHoraCierre()
            ));
        }
    }
}