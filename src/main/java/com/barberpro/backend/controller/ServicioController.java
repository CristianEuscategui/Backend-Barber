package com.barberpro.backend.controller;

import com.barberpro.backend.dto.ServicioDTO;
import com.barberpro.backend.entity.Servicio;
import com.barberpro.backend.service.ServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<Servicio>> listar() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody ServicioDTO dto) {
        return new ResponseEntity<>(servicioService.guardar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Long id, @RequestBody ServicioDTO dto) {
        return ResponseEntity.ok(servicioService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
       
     @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable Long id) {
    return ResponseEntity.ok(servicioService.obtenerPorId(id));
}
}