package com.barberpro.backend.service;

import com.barberpro.backend.dto.BarberoDTO;
import com.barberpro.backend.entity.Barbero;
import com.barberpro.backend.repository.BarberoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberoService {

    private final BarberoRepository barberoRepository;

    public List<Barbero> listarActivos() {
        return barberoRepository.findByActivoTrue();
    }

    public List<Barbero> listarTodos() {
        return barberoRepository.findAll();
    }

    public Barbero obtenerPorId(Long id) {
        return barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con el ID: " + id));
    }

    public Barbero guardar(BarberoDTO dto) {
        Barbero barbero = new Barbero();
        barbero.setNombre(dto.getNombre());
        barbero.setApellido(dto.getApellido());
        barbero.setEspecialidad(dto.getEspecialidad());
        barbero.setTelefono(dto.getTelefono());
        barbero.setEmail(dto.getEmail());
        barbero.setFotoUrl(dto.getFotoUrl()); // <-- nuevo
        barbero.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return barberoRepository.save(barbero);
    }

    public Barbero cambiarEstado(Long id, Boolean activo) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));
        barbero.setActivo(activo);
        return barberoRepository.save(barbero);
    }

    public Barbero actualizar(Long id, BarberoDTO dto) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con el ID: " + id));

        barbero.setNombre(dto.getNombre());
        barbero.setApellido(dto.getApellido());
        barbero.setEspecialidad(dto.getEspecialidad());
        barbero.setTelefono(dto.getTelefono());
        barbero.setEmail(dto.getEmail());
        barbero.setFotoUrl(dto.getFotoUrl()); // <-- nuevo

        return barberoRepository.save(barbero);
    }

    // Nuevo: usado por el endpoint de subida de foto
    public void actualizarFoto(Long id, String fotoUrl) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));
        barbero.setFotoUrl(fotoUrl);
        barberoRepository.save(barbero);
    }
}