package com.barberpro.backend.repository;

import com.barberpro.backend.entity.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BarberoRepository extends JpaRepository<Barbero, Long> {
    List<Barbero> findByActivoTrue();
}