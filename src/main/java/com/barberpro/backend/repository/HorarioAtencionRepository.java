package com.barberpro.backend.repository;

import com.barberpro.backend.entity.HorarioAtencion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface HorarioAtencionRepository extends JpaRepository<HorarioAtencion, Long> {
    Optional<HorarioAtencion> findByDiaSemana(DayOfWeek diaSemana);
    List<HorarioAtencion> findAllByOrderByDiaSemanaAsc();
}