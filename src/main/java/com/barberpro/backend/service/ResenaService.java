package com.barberpro.backend.service;

import com.barberpro.backend.dto.ResenaRequestDTO;
import com.barberpro.backend.dto.ResenaResponseDTO;
import com.barberpro.backend.entity.Cita;
import com.barberpro.backend.entity.EstadoCita;
import com.barberpro.backend.entity.Resena;
import com.barberpro.backend.repository.CitaRepository;
import com.barberpro.backend.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final CitaRepository citaRepository;

    public ResenaResponseDTO crear(ResenaRequestDTO req) {
        Cita cita = citaRepository.findById(req.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (cita.getEstado() != EstadoCita.TERMINADA) {
            throw new RuntimeException("Solo puedes dejar una reseña de una cita ya terminada.");
        }

        if (resenaRepository.findByCitaId(cita.getId()).isPresent()) {
            throw new RuntimeException("Ya existe una reseña para esta cita.");
        }

        if (req.getEstrellas() == null || req.getEstrellas() < 1 || req.getEstrellas() > 5) {
            throw new RuntimeException("La calificación debe ser entre 1 y 5 estrellas.");
        }

        Resena resena = Resena.builder()
                .cita(cita)
                .barbero(cita.getBarbero())
                .clienteNombre(cita.getClienteNombre())
                .estrellas(req.getEstrellas())
                .comentario(req.getComentario())
                .fecha(LocalDateTime.now())
                .verificada(true)
                .build();

        return convertirADto(resenaRepository.save(resena));
    }

    public List<ResenaResponseDTO> listarPorBarbero(Long barberoId) {
        return resenaRepository.findByBarberoIdOrderByFechaDesc(barberoId)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public void eliminar(Long id) {
        if (!resenaRepository.existsById(id)) {
            throw new RuntimeException("Reseña no encontrada");
        }
        resenaRepository.deleteById(id);
    }

    private ResenaResponseDTO convertirADto(Resena r) {
        return ResenaResponseDTO.builder()
                .id(r.getId())
                .clienteNombre(r.getClienteNombre())
                .barberoId(r.getBarbero().getId())
                .barberoNombre(r.getBarbero().getNombre() + " " + r.getBarbero().getApellido())
                .estrellas(r.getEstrellas())
                .comentario(r.getComentario())
                .fecha(r.getFecha())
                .verificada(r.getVerificada())
                .build();
    }
}