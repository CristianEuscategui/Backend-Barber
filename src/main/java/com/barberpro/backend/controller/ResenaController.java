package com.barberpro.backend.controller;

import com.barberpro.backend.dto.ResenaRequestDTO;
import com.barberpro.backend.dto.ResenaResponseDTO;
import com.barberpro.backend.service.ResenaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crear(@RequestBody ResenaRequestDTO dto) {
        return new ResponseEntity<>(resenaService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<ResenaResponseDTO>> listarPorBarbero(@PathVariable Long barberoId) {
        return ResponseEntity.ok(resenaService.listarPorBarbero(barberoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}