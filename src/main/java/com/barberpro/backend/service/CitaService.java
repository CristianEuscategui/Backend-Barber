package com.barberpro.backend.service;

import com.barberpro.backend.dto.CitaRequestDTO;
import com.barberpro.backend.dto.CitaResponseDTO;
import com.barberpro.backend.entity.*;
import com.barberpro.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.barberpro.backend.service.HorarioAtencionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final BarberoRepository barberoRepository;
    private final ServicioRepository servicioRepository;
    private final WhatsAppService whatsAppService;
    private final HorarioAtencionService horarioAtencionService;

    public CitaResponseDTO registrarCita(CitaRequestDTO req) {
        Barbero barbero = barberoRepository.findById(req.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));

        Servicio servicio = servicioRepository.findById(req.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // 1. Unir LocalDate y LocalTime del DTO en un LocalDateTime
        LocalDateTime fechaHoraExacta = LocalDateTime.of(req.getFecha(), req.getHora());
        // Después de: LocalDateTime fechaHoraExacta = LocalDateTime.of(req.getFecha(), req.getHora());
        horarioAtencionService.validarDentroDeHorario(fechaHoraExacta);

        // 2. Validar cruce de citas
        List<Cita> cruces = citaRepository.findByBarberoIdAndFechaHoraAndEstadoNot(
                req.getBarberoId(), fechaHoraExacta, EstadoCita.CANCELADA
        );

        if (!cruces.isEmpty()) {
            throw new RuntimeException("El barbero seleccionado ya tiene una cita agendada en ese horario.");
        }

        // 3. Crear entidad y guardar
        Cita cita = Cita.builder()
                .clienteNombre(req.getClienteNombre())
                .clienteTelefono(req.getClienteTelefono())
                .barbero(barbero)
                .servicio(servicio)
                .fechaHora(fechaHoraExacta)
                .estado(EstadoCita.PENDIENTE)
                .notas(req.getNotas())
                .build();

        Cita guardada = citaRepository.save(cita);

        return convertirADto(guardada);
    }

    public List<CitaResponseDTO> listarPorBarbero(Long barberoId) {
        List<Cita> citas = (barberoId != null) 
                ? citaRepository.findByBarberoId(barberoId) 
                : citaRepository.findAll();

        return citas.stream()
                .map(this::convertirADto)
                .toList();
    }

    public CitaResponseDTO obtenerPorIdDTO(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con el ID: " + id));
        return convertirADto(cita);
    }

    public CitaResponseDTO cambiarEstado(Long id, EstadoCita nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con el ID: " + id));

        cita.setEstado(nuevoEstado);
        Cita citaActualizada = citaRepository.save(cita);

        return convertirADto(citaActualizada); // Corregido: usa convertirADto
    }

    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con el ID: " + id);
        }
        citaRepository.deleteById(id);
    }
    public Cita obtenerEntidadPorId(Long id) {
    return citaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con el ID: " + id));
}

    public CitaResponseDTO convertirADto(Cita cita) {
        return CitaResponseDTO.builder()
                .id(cita.getId())
                .clienteNombre(cita.getClienteNombre())
                .clienteTelefono(cita.getClienteTelefono())
                .barberoNombre(cita.getBarbero() != null 
                        ? cita.getBarbero().getNombre() + " " + cita.getBarbero().getApellido() 
                        : null)
                .servicioNombre(cita.getServicio() != null ? cita.getServicio().getNombre() : null)
                .precio(cita.getServicio() != null ? cita.getServicio().getPrecio() : null)
                .fecha(cita.getFechaHora().toLocalDate())
                .hora(cita.getFechaHora().toLocalTime())
                .estado(cita.getEstado())
                .notas(cita.getNotas())
                .whatsappUrl(whatsAppService.generarLinkWhatsApp(cita))
                .build();
    }
}