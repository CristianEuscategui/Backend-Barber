package com.barberpro.backend.controller;

import com.barberpro.backend.dto.HorarioAtencionDTO;
import com.barberpro.backend.entity.HorarioAtencion;
import com.barberpro.backend.service.HorarioAtencionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioAtencionController {

    private final HorarioAtencionService horarioService;

    @GetMapping
    public ResponseEntity<List<HorarioAtencion>> listar() {
        return ResponseEntity.ok(horarioService.listarTodos());
    }

    @PutMapping("/{dia}")
    public ResponseEntity<HorarioAtencion> actualizar(@PathVariable DayOfWeek dia, @RequestBody HorarioAtencionDTO dto) {
        return ResponseEntity.ok(horarioService.actualizar(dia, dto));
    }
}