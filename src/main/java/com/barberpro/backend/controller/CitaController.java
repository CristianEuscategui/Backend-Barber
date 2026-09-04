package com.barberpro.backend.controller;


import com.barberpro.backend.dto.CitaRequestDTO;
import com.barberpro.backend.dto.CitaResponseDTO;
import com.barberpro.backend.entity.Cita;
import com.barberpro.backend.entity.EstadoCita;
import com.barberpro.backend.service.CitaService;
import com.barberpro.backend.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;
    private final WhatsAppService whatsAppService;

    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@RequestBody CitaRequestDTO dto) {
        return new ResponseEntity<>(citaService.registrarCita(dto), HttpStatus.CREATED);
    }

    // Cambiado: ahora devuelve List<CitaResponseDTO>
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar(@RequestParam(required = false) Long barberoId) {
        return ResponseEntity.ok(citaService.listarPorBarbero(barberoId));
    }

    // Cambiar estado (PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam EstadoCita estado) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, estado));
    }

    // Eliminar una cita por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Cambiado: ahora devuelve CitaResponseDTO
    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorIdDTO(id));
    }
    @GetMapping("/{id}/link-resena")
    public ResponseEntity<String> obtenerLinkResena(@PathVariable Long id) {
    Cita cita = citaService.obtenerEntidadPorId(id); // necesitamos este método nuevo
    return ResponseEntity.ok(whatsAppService.generarLinkResena(cita));
    }

    @GetMapping("/{id}/link-notificacion-barbero")
public ResponseEntity<String> obtenerLinkNotificacionBarbero(@PathVariable Long id) {
    Cita cita = citaService.obtenerEntidadPorId(id);
    return ResponseEntity.ok(whatsAppService.generarLinkNotificacionBarbero(cita));
}
@GetMapping("/{id}/link-cancelacion")
public ResponseEntity<String> obtenerLinkCancelacion(@PathVariable Long id) {
    Cita cita = citaService.obtenerEntidadPorId(id);
    return ResponseEntity.ok(whatsAppService.generarLinkCancelacion(cita));
}
}