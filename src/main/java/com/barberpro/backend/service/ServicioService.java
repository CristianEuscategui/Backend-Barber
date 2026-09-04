package com.barberpro.backend.service;

import com.barberpro.backend.dto.ServicioDTO;
import com.barberpro.backend.entity.Servicio;
import com.barberpro.backend.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }
    public Servicio obtenerPorId(Long id) {
    return servicioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Servicio no encontrado con el ID: " + id));
}

    public Servicio guardar(ServicioDTO dto) {
        Servicio servicio = Servicio.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .duracionMinutos(dto.getDuracionMinutos())
                .build();
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, ServicioDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setNombre(dto.getNombre());
        servicio.setPrecio(dto.getPrecio());
        servicio.setDuracionMinutos(dto.getDuracionMinutos());
        return servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
    if (!servicioRepository.existsById(id)) {
        throw new RuntimeException("Servicio no encontrado con el ID: " + id);
    }
    try {
        servicioRepository.deleteById(id);
    } catch (org.springframework.dao.DataIntegrityViolationException e) {
        throw new RuntimeException("No se puede eliminar este servicio porque ya tiene citas asociadas. Considera dejarlo sin usar en vez de eliminarlo.");
    }
}
}