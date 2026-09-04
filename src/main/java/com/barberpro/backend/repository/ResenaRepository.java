package com.barberpro.backend.repository;

import com.barberpro.backend.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByBarberoIdOrderByFechaDesc(Long barberoId);
    Optional<Resena> findByCitaId(Long citaId);
}