package com.barberpro.backend.config;

import com.barberpro.backend.entity.Admin;
import com.barberpro.backend.entity.HorarioAtencion;
import com.barberpro.backend.repository.AdminRepository;
import com.barberpro.backend.repository.HorarioAtencionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final HorarioAtencionRepository horarioAtencionRepository;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .usuario("admin")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .preguntaSeguridad("¿Cuál es el nombre de tu primera barbería?")
                    .respuestaSeguridadHash(passwordEncoder.encode("cambiar"))
                    .build();
            adminRepository.save(admin);
            System.out.println("Admin inicial creado: usuario=admin, password=admin123 (cámbiala luego desde el panel)");
        }

        if (horarioAtencionRepository.count() == 0) {
            for (DayOfWeek dia : DayOfWeek.values()) {
                boolean esDomingo = dia == DayOfWeek.SUNDAY;
                HorarioAtencion horario = HorarioAtencion.builder()
                        .diaSemana(dia)
                        .abierto(true)
                        .horaApertura(LocalTime.of(10, 0))
                        .horaCierre(esDomingo ? LocalTime.of(18, 0) : LocalTime.of(22, 0))
                        .build();
                horarioAtencionRepository.save(horario);
            }
            System.out.println("Horarios de atención iniciales creados (10am-10pm, domingo hasta 6pm).");
        }
    }
}