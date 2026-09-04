package com.barberpro.backend.controller;

import com.barberpro.backend.dto.*;
import com.barberpro.backend.entity.Admin;
import com.barberpro.backend.repository.AdminRepository;
import com.barberpro.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        Admin admin = adminRepository.findByUsuario(req.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        if (!passwordEncoder.matches(req.getPassword(), admin.getPasswordHash())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        String token = jwtUtil.generarToken(admin.getUsuario());
        return ResponseEntity.ok(new LoginResponseDTO(token, admin.getUsuario()));
    }

    @PutMapping("/credenciales")
    public ResponseEntity<String> cambiarCredenciales(@RequestBody CambiarCredencialesDTO dto) {
        Admin admin = adminRepository.findByUsuario(dto.getUsuarioActual())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPasswordActual(), admin.getPasswordHash())) {
            throw new RuntimeException("La contraseña actual no es correcta.");
        }

        if (dto.getNuevoUsuario() != null && !dto.getNuevoUsuario().isBlank()) {
            admin.setUsuario(dto.getNuevoUsuario());
        }
        if (dto.getNuevaPassword() != null && !dto.getNuevaPassword().isBlank()) {
            admin.setPasswordHash(passwordEncoder.encode(dto.getNuevaPassword()));
        }
        if (dto.getPreguntaSeguridad() != null && !dto.getPreguntaSeguridad().isBlank()
                && dto.getRespuestaSeguridad() != null && !dto.getRespuestaSeguridad().isBlank()) {
            admin.setPreguntaSeguridad(dto.getPreguntaSeguridad());
            admin.setRespuestaSeguridadHash(passwordEncoder.encode(dto.getRespuestaSeguridad()));
        }

        adminRepository.save(admin);
        return ResponseEntity.ok("Credenciales actualizadas correctamente.");
    }

    @GetMapping("/pregunta-seguridad/{usuario}")
    public ResponseEntity<String> obtenerPreguntaSeguridad(@PathVariable String usuario) {
        Admin admin = adminRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (admin.getPreguntaSeguridad() == null || admin.getPreguntaSeguridad().isBlank()) {
            throw new RuntimeException("Este usuario no tiene una pregunta de seguridad configurada.");
        }

        return ResponseEntity.ok(admin.getPreguntaSeguridad());
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<String> recuperarPassword(@RequestBody RecuperarPasswordDTO dto) {
        Admin admin = adminRepository.findByUsuario(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (admin.getRespuestaSeguridadHash() == null) {
            throw new RuntimeException("Este usuario no tiene una pregunta de seguridad configurada.");
        }

        if (!passwordEncoder.matches(dto.getRespuestaSeguridad(), admin.getRespuestaSeguridadHash())) {
            throw new RuntimeException("La respuesta no coincide con la registrada.");
        }

        admin.setPasswordHash(passwordEncoder.encode(dto.getNuevaPassword()));
        adminRepository.save(admin);
        return ResponseEntity.ok("Contraseña restablecida correctamente.");
    }
}