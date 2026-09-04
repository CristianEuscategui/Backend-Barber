package com.barberpro.backend.repository;

import com.barberpro.backend.entity.Cita;
import com.barberpro.backend.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByBarberoIdAndFechaHoraAndEstadoNot(Long barberoId, LocalDateTime fechaHora, EstadoCita estado);
    List<Cita> findByBarberoId(Long barberoId);
}