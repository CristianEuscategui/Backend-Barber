package com.barberpro.backend.controller;

import com.barberpro.backend.dto.BarberoDTO;
import com.barberpro.backend.entity.Barbero;
import com.barberpro.backend.service.BarberoService;
import com.barberpro.backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@RequiredArgsConstructor
public class BarberoController {

    private final BarberoService barberoService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<Barbero>> listarActivos() {
        return ResponseEntity.ok(barberoService.listarActivos());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Barbero>> listarTodos() {
        return ResponseEntity.ok(barberoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Barbero> crear(@RequestBody BarberoDTO dto) {
        return new ResponseEntity<>(barberoService.guardar(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Barbero> cambiarEstado(@PathVariable Long id, @RequestParam Boolean activo) {
        return ResponseEntity.ok(barberoService.cambiarEstado(id, activo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Barbero> actualizar(@PathVariable Long id, @RequestBody BarberoDTO dto) {
        return ResponseEntity.ok(barberoService.actualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barbero> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(barberoService.obtenerPorId(id));
    }

    @PostMapping("/{id}/foto")
    public ResponseEntity<String> subirFoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String url = fileStorageService.guardarImagen(file);
        barberoService.actualizarFoto(id, url);
        return ResponseEntity.ok(url);
    }
}